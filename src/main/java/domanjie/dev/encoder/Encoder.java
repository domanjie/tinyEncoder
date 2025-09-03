package domanjie.dev.encoder;

import domanjie.dev.encoder.entropyEncoder.AcHuffmanEntropyEncoder;
import domanjie.dev.encoder.entropyEncoder.DcHuffmanEntropyEncoder;
import domanjie.dev.jpeg.*;
import domanjie.dev.encoder.entropyEncoder.EntropyEncodedDataStore;
import domanjie.dev.utils.TwoDIntArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Encoder {
    //data unit represents 8x8 block in dct-based processes
    private static final int DataUnitL=8;
    private final int noOfThread;
    public Encoder() {
        noOfThread=1;
    }
    public Encoder(int noOfThread){
        if (noOfThread<1){
            throw new IllegalArgumentException("Minimum of 1 thread is Needed for encoding.");
        }
        this.noOfThread=noOfThread;
    }
    public  JpegImage encode(ImageComponent[] components){
        int maxVerticalSamplingFactor= Arrays
                .stream(components)
                .map(imageComponent-> imageComponent.samplingFactor().verticalSamplingFactor())
                .max(Comparator.comparingInt(Integer::intValue)).get();
        int maxHorizontalSamplingFactor=Arrays
                .stream(components)
                .map(imageComponent-> imageComponent.samplingFactor().horizontalSamplingFactor())
                .max(Comparator.comparingInt(Integer::intValue)).get();
        int noRowsMcu= DataUnitL * maxVerticalSamplingFactor;
        int noColsMcu= DataUnitL * maxHorizontalSamplingFactor ;
        var maxNoLines= components[0].data().length;
        var maxSamplesPerLines=components[0].data()[0].length;
        int componentRowToMCURowRatio= (int) Math.ceil((double) components[0].data().length /noRowsMcu);
        int componentColToMCUColRatio= (int) Math.ceil((double) components[0].data()[0].length /noColsMcu);
        var totalMCUs=componentColToMCUColRatio*componentRowToMCURowRatio;
        int MCUsPerThread=Math.min(totalMCUs/noOfThread,65_535);
        var MCUsLeft= totalMCUs-(MCUsPerThread * noOfThread );
        var ecsAggregator= new  Vector<List<Byte>>();
        /*
        Initialize the size of the entropy encoded segment aggregator.
        This prevents ArrayIndexOutOfBounds exception as the order
        in which threads store their partition is non-deterministic.
        */
        while (ecsAggregator.size()< noOfThread)ecsAggregator.add(null);
        var workerThreads=new Thread[noOfThread-1];
        var mainTStartMCU=totalMCUs-(MCUsPerThread+MCUsLeft);
        var currentMCU=0;
        var partition= 0;
        /*
          chunk MCUs and process them in separate threads.
          The last chunk is left to be processed by the main thread.
          Notice that This loop never runs if the @Param  is 1
        */
        while(currentMCU!=mainTStartMCU){
            var workerTStartMCU=currentMCU;
            int workerTEndMCU=workerTStartMCU+MCUsPerThread;
            int effectivelyFinalPartition = partition;
            workerThreads[partition]= new Thread(()->{
                var imageData =  processSection(
                        components,
                        workerTStartMCU,
                        workerTEndMCU,
                        componentRowToMCURowRatio,
                        componentColToMCUColRatio,
                        noRowsMcu,noColsMcu,
                        maxVerticalSamplingFactor,
                        maxHorizontalSamplingFactor
                );
                ecsAggregator.set(effectivelyFinalPartition,imageData);
            });
            partition++;
            currentMCU=workerTEndMCU;
        }
        for(var thread: workerThreads ){
            thread.start();
        }
        //Main thread processes the last chunk of MCUs
        var imageData= processSection(
                components,
                mainTStartMCU,
                totalMCUs,
                componentRowToMCURowRatio,
                componentColToMCUColRatio,
                noRowsMcu,noColsMcu,
                maxVerticalSamplingFactor,
                maxHorizontalSamplingFactor
        );
        ecsAggregator.set(partition,imageData);
        for (Thread thread : workerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        var scan= new Scan(components, 0, 63, 0,0,noOfThread>1? MCUsPerThread:0,ecsAggregator);
        return new JpegImage(components, List.of(scan),maxNoLines, maxSamplesPerLines );
    }
    private List<Byte> processSection(ImageComponent[] components, int startIndex, int endIndex, int componentRowToMCURowRatio, int  componentColToMCUColRatio, int noRowsMcu, int noColsMcu, int maxVerticalSamplingFactor, int maxHorizontalSamplingFactor){
        var imageData=new EntropyEncodedDataStore();
        var componentAcEntropyEncoderMap=
                Arrays.stream(components).collect(Collectors.toMap(
                        imageComponent -> imageComponent,
                        imageComponent -> new AcHuffmanEntropyEncoder(imageComponent.acEntropyEncodingTable().bitList(),imageComponent.acEntropyEncodingTable().huffVal()))) ;
        var componentDcEntropyEncoderMap=
                Arrays.stream(components).collect(Collectors.toMap(
                        imageComponent -> imageComponent,
                        imageComponent -> new DcHuffmanEntropyEncoder(imageComponent.dcEntropyEncodingTable().bitList(),imageComponent.dcEntropyEncodingTable().huffVal())));
        for (int i =startIndex; i<endIndex ; i++){
            for (var component: components ) {
                //map i back to 2d
                int  row= i/componentColToMCUColRatio * noRowsMcu;
                int  col= i%componentColToMCUColRatio * noColsMcu;
                var mcu= TwoDIntArrayUtils.getSubMatrix(row, Math.min(row+noRowsMcu,component.data().length),col,Math.min(col+noColsMcu ,component.data()[0].length),component.data());
                mcu =padMcuIfNeeded(mcu, noRowsMcu,noColsMcu);
                var sampledComponent= SubSampler.subSampleComponent(mcu,  component.samplingFactor(),maxHorizontalSamplingFactor, maxVerticalSamplingFactor);
                var leveShiftedComponent= levelShiftComponent(sampledComponent, 8);
                transformQuantizeAndEncodeComponent(leveShiftedComponent,component.quantTable(),
                        componentAcEntropyEncoderMap.get(component),
                        componentDcEntropyEncoderMap.get(component),
                        imageData);
            }
        }
        return imageData.getData();
    }

    private int[][] levelShiftComponent(int[][] sampledComponent, int precision) {
        var shiftBy=Math.pow(2,precision-1);
        for (var row = 0 ;row <sampledComponent.length;row++){
            for (var col=0; col<sampledComponent[0].length;col++){
                sampledComponent[row][col]-= (int) shiftBy;
            }
        }
        return  sampledComponent;
    }
    private int[][] padMcuIfNeeded(int[][] mcu , int noRowsMcu, int noColsMcu ){
        int[][] result;
        if(mcu.length<noRowsMcu){
            var padBy=noRowsMcu-mcu.length;
            mcu= TwoDIntArrayUtils.padRow(mcu,padBy);
        }
        if(mcu[0].length<noColsMcu){
            var padBy=noColsMcu-mcu[0].length;
            mcu= TwoDIntArrayUtils.padColumn(mcu, padBy);
        }
        return mcu;
    }

    public void transformQuantizeAndEncodeComponent(int[][] component, QuantTable componentQuantTable,
                                                     AcHuffmanEntropyEncoder acHuffmanEntropyEncoder,
                                                     DcHuffmanEntropyEncoder dcHuffmanEntropyEncoder,
                                                     EntropyEncodedDataStore imageData) {
        for (int m=0;m<component.length;m+=8){
            for(int n =0;n<component[0] .length;n+=8) {
                var subMatrix = TwoDIntArrayUtils.getSubMatrix(m, m + 8, n, n + 8, component);
                var quantizedDctBlock= transformAndQuantize8x8Block(componentQuantTable,new _8x8Block(subMatrix));
                var zigZagSequence= generateZigZagSequenceFrom8x8Block(quantizedDctBlock.val());
                var dcEncodedVar =  dcHuffmanEntropyEncoder.encode(zigZagSequence[0]);
                imageData.addBits(dcEncodedVar.val(), dcEncodedVar.encodedValSize());
                int runOfZeros = 0;
                for (int s = 1; s < zigZagSequence.length; s++){
                    if (zigZagSequence[s] == 0) {
                        runOfZeros++;
                        if(s==63){
                            var encodedAcVar=acHuffmanEntropyEncoder.encode(0, 0);
                            imageData.addBits(encodedAcVar.val(), encodedAcVar.encodedValSize());
                        }
                        continue;
                    }
                    while(runOfZeros>15){
                        var encodedAcVar= acHuffmanEntropyEncoder.encode(15, 0);
                        imageData.addBits(encodedAcVar.val(), encodedAcVar.encodedValSize());
                        runOfZeros-=16;
                    }
                    var encodedAcVar = acHuffmanEntropyEncoder.encode(runOfZeros,zigZagSequence[s]);
                    imageData.addBits(encodedAcVar.val(), encodedAcVar.encodedValSize());
                    runOfZeros = 0;
                }
            }
        }
    }

    private _8x8Block transformAndQuantize8x8Block(QuantTable quantizationTable,_8x8Block block){
        var transformedDctCoefficients= TwoDDctTransformer.twoDimensionalForwardDct( block);
        return Quantizer.quantize(quantizationTable.table(),transformedDctCoefficients);
    }

    static int[] generateZigZagSequenceFrom8x8Block(int[][] i){
        return new int[]
                {
                        i[0][0], i[0][1], i[1][0], i[2][0],  i[1][1], i[0][2], i[0][3], i[1][2],
                        i[2][1], i[3][0], i[4][0], i[3][1],  i[2][2], i[1][3], i[0][4], i[0][5],
                        i[1][4], i[2][3], i[3][2], i[4][1],  i[5][0], i[6][0], i[5][1], i[4][2],
                        i[3][3], i[2][4], i[1][5], i[0][6],  i[0][7], i[1][6], i[2][5], i[3][4],
                        i[4][3], i[5][2], i[6][1], i[7][0],  i[7][1], i[6][2], i[5][3], i[4][4],
                        i[3][5], i[2][6], i[1][7],  i[2][7], i[3][6], i[4][5], i[5][4], i[6][3],
                        i[7][2], i[7][3], i[6][4],  i[5][5], i[4][6], i[3][7], i[4][7], i[5][6],
                        i[6][5], i[7][4], i[7][5],  i[6][6], i[5][7], i[6][7], i[7][6], i[7][7],
                };
    }
}
