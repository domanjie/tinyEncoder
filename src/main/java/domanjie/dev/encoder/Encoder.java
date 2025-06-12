package domanjie.dev.encoder;

import domanjie.dev.encoder.entropyEncoder.AcHuffmanEntropyEncoder;
import domanjie.dev.encoder.entropyEncoder.DcHuffmanEntropyEncoder;
import domanjie.dev.jpeg.*;
import domanjie.dev.encoder.entropyEncoder.EntropyEncodedDataStore;
import domanjie.dev.utils.TwoDIntArrayUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class Encoder {
    //data unit represents 8x8 block in dct-based processes
    private static final int DataUnitL=8;
;

    public Encoder() {
    }
    public Encoder(QuantTable lumaQuanTable, QuantTable chromaQuantTable){
    }
    public  JpegImage encode(ImageComponent[] components){
        var imageData=new EntropyEncodedDataStore();
        var maxVerticalSamplingFactor= Arrays.stream(components).map(imageComponent-> imageComponent.samplingFactor().verticalSamplingFactor()).max(Comparator.comparingInt(Integer::intValue)).get();
        var maxHorizontalSamplingFactor=Arrays.stream(components).map(imageComponent-> imageComponent.samplingFactor().horizontalSamplingFactor()).max(Comparator.comparingInt(Integer::intValue)).get();
        var maxNoRowsMcu= DataUnitL * maxVerticalSamplingFactor;
        var maxNoColsMcu= DataUnitL * maxHorizontalSamplingFactor ;
//
//        var maxNoLines=components[0].data().length  % maxNoRowsMcu ==0?components[0].data().length : components[0].data().length +maxNoRowsMcu - (components[0].data().length %maxNoRowsMcu);
//        var maxSamplesPerLines=components[0].data()[0].length % maxNoColsMcu ==0
//                ? components[0].data()[0].length
//                : components[0].data()[0].length +maxNoColsMcu - (components[0].data()[0].length%maxNoColsMcu);

        var maxNoLines= components[0].data().length;
        var maxSamplesPerLines=components[0].data()[0].length;
        var componentAcEntropyEncoderMap=
                Arrays.stream(components).collect(Collectors.toMap(
                        imageComponent -> imageComponent,
                        imageComponent -> new AcHuffmanEntropyEncoder(imageComponent.acEntropyEncodingTable().bitList(),imageComponent.acEntropyEncodingTable().huffVal()))) ;
        var componentDcEntropyEncoderMap=
                Arrays.stream(components).collect(Collectors.toMap(
                        imageComponent -> imageComponent,
                        imageComponent -> new DcHuffmanEntropyEncoder(imageComponent.dcEntropyEncodingTable().bitList(),imageComponent.dcEntropyEncodingTable().huffVal())));
        ;

        for (int i = 0; i < components[0].data().length ; i+=maxNoRowsMcu) {
            for (int j = 0; j < components[0].data()[0].length; j+=maxNoColsMcu) {
                for (var component:components ) {
                    var x= fetchMcu(i , j,component.data(),maxNoRowsMcu,maxNoColsMcu);
                    var sampledComponent= SubSampler.subSampleComponent(x,  component.samplingFactor(),maxHorizontalSamplingFactor, maxVerticalSamplingFactor);
                    var leveShiftedComponent= levelShiftComponent(sampledComponent, 8);
                    transformQuantizeAndEncodeComponent(leveShiftedComponent,component.quantTable(),
                            componentAcEntropyEncoderMap.get(component),
                            componentDcEntropyEncoderMap.get(component),
                            imageData);
                };
            }
        }
        var scan= new Scan(components, 0, 63, 0,0,imageData.getData());
        return new JpegImage(components, Set.of(scan),maxNoLines, maxSamplesPerLines );
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

    private int[][] fetchMcu(int rowIndex, int colIndex, int[][] componentData, int maxNoRowsMcu, int maxNoColsMcu){
        int[][] subPixelArray;
        if(rowPadNeeded(rowIndex,maxNoRowsMcu,componentData) && columnPadNeeded(colIndex, maxNoColsMcu, componentData)){
            subPixelArray=TwoDIntArrayUtils.getSubMatrix(rowIndex, componentData.length,colIndex,componentData[0].length,componentData);
            var padRowBy =rowIndex+maxNoRowsMcu-componentData.length;
            subPixelArray=TwoDIntArrayUtils.padRow(subPixelArray,padRowBy);
            var padColumnBy= colIndex+maxNoColsMcu-componentData[0].length;
            subPixelArray= TwoDIntArrayUtils.padColumn(subPixelArray, padColumnBy);
        }else if(rowPadNeeded(rowIndex,maxNoRowsMcu,componentData)){
            subPixelArray= TwoDIntArrayUtils.getSubMatrix(rowIndex, componentData.length, colIndex,colIndex+maxNoColsMcu ,componentData);
            var padBy=rowIndex+maxNoRowsMcu-componentData.length;
            subPixelArray= TwoDIntArrayUtils.padRow(subPixelArray,padBy);
        }
        else if(columnPadNeeded(colIndex, maxNoColsMcu, componentData)){
            subPixelArray=TwoDIntArrayUtils.getSubMatrix(rowIndex, rowIndex+maxNoRowsMcu,colIndex,componentData[0].length,componentData);
            var padBy=maxNoColsMcu+colIndex-componentData[0].length;
            subPixelArray= TwoDIntArrayUtils.padColumn(subPixelArray, padBy);
        }
        else{
            subPixelArray=TwoDIntArrayUtils.getSubMatrix(rowIndex, rowIndex+maxNoRowsMcu,colIndex,colIndex+maxNoColsMcu,componentData
            );
        }
        return  subPixelArray;
    }
    private boolean rowPadNeeded(int currentRowIndex,int maxNoRowsMcu , int[][] pixelArray){
        return currentRowIndex+maxNoRowsMcu>=pixelArray.length;
    }
    public boolean columnPadNeeded(int currentColumnIndex,int maxNoColsMcu , int[][] pixelArray){
        return currentColumnIndex+maxNoColsMcu>=pixelArray[0].length;
    }
    public void transformQuantizeAndEncodeComponent(int[][] component, QuantTable componentQuantTable,
                                                     AcHuffmanEntropyEncoder acHuffmanEntropyEncoder,
                                                     DcHuffmanEntropyEncoder dcHuffmanEntropyEncoder,
                                                     EntropyEncodedDataStore imageData) {
        for (int m=0;m<component.length;m+=8){
            for(int n =0;n<component[0] .length;n+=8) {
                var _8x8Block = TwoDIntArrayUtils.getSubMatrix(m, m + 8, n, n + 8, component);
                var quantizedDctCoefficients= transformAndQuantize8x8Block(componentQuantTable,_8x8Block);
                var zigZagSequence= generateZigZagSequenceFrom8x8Block(quantizedDctCoefficients);
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

    private int [][] transformAndQuantize8x8Block(QuantTable quantizationTable,int [][]block){
        var transformedDctCoefficients=TwoWayDctTransformer.twoDimensionalForwardDct(block);
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
