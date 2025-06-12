package domanjie.dev.jpeg;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JpegImage {
    //this defines the max number of rows amongst all components in the frame
    private final int  maxNoLines;
    //this defines the max number of columns  all components in the frame
    private final int  maxNoSamplesPerLine;
    private final ImageComponent[] imageComponents;
    private final Set<Scan> scans;
    private final int  frameSamplePrecision=8;


    public JpegImage(ImageComponent[] imageComponents,Set<Scan> scans,
                     int maxNoLines, int maxNoSamplesPerLine) {
        this.imageComponents = imageComponents;
        this.scans = scans;
        this.maxNoLines = maxNoLines;
        this.maxNoSamplesPerLine = maxNoSamplesPerLine;
    }
    public void toFile(String fileName)  {
        try(
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                var dataOutputStream=new DataOutputStream(byteArrayOutputStream)) {

            dataOutputStream.writeShort(JpegMarker.SOI);
            var quantTables =Arrays.stream(imageComponents).map(ImageComponent::quantTable).collect(Collectors.toSet());
            for(var quantTable: quantTables){
                dataOutputStream.writeShort(JpegMarker.DQT);
                dataOutputStream.writeShort(quantTable.length());
                var elementPrecision$TableDestinationIdentifier = quantTable.precision().val() << 4|quantTable.tableDestinationIdentifier().val();
                dataOutputStream.write(elementPrecision$TableDestinationIdentifier);
                switch (quantTable.precision()) {
                    case _8BIT -> {
                        for(var  row: quantTable.table()){
                            for(var val : row ){
                                dataOutputStream.writeByte(val);
                            }
                        }
                    }
                    case _16BIT -> {
                        for(var  row: quantTable.table()){
                            for(var val : row ){
                                dataOutputStream.writeShort(val);
                            }
                        }
                    }
                }

            }

            var noComponentsInFrame= imageComponents.length;
            var frameHeaderLength=8 +3* noComponentsInFrame;

            dataOutputStream.writeShort(JpegMarker.SOF1);
            dataOutputStream.writeShort(frameHeaderLength);
            dataOutputStream.writeByte(frameSamplePrecision);
            dataOutputStream.writeShort(maxNoLines);
            dataOutputStream.writeShort(maxNoSamplesPerLine);
            dataOutputStream.writeByte(noComponentsInFrame);

            for (var component: imageComponents) {
                var packedSamplingFactor=  component.samplingFactor().horizontalSamplingFactor()<<4 | component.samplingFactor().verticalSamplingFactor();
                dataOutputStream.writeByte(component.label());
                dataOutputStream.writeByte(packedSamplingFactor);
                dataOutputStream.writeByte(component.quantTable().tableDestinationIdentifier().val());
            }

            var huffmanTables= Arrays.stream(imageComponents).flatMap(imageComponent
                    -> Stream.of(imageComponent.acEntropyEncodingTable(), imageComponent.dcEntropyEncodingTable())).collect(Collectors.toSet());

            for (var huffTable: huffmanTables){
                var huffTableLength=19+huffTable.huffVal().length;
                var packedTableClassAndHuffmanTableDestinationIdentifier=huffTable.tableClass().val()<< 4| huffTable.tableDestinationIdentifier().val();
                dataOutputStream.writeShort(JpegMarker.DHT);
                dataOutputStream.writeShort(huffTableLength);
                dataOutputStream.writeByte(packedTableClassAndHuffmanTableDestinationIdentifier);
                writeBytes(dataOutputStream,huffTable.bitList());
                writeBytes(dataOutputStream,huffTable.huffVal());
            }
            for(var scan : scans){
                var noComponentsInScan=scan.components().length;
                var scanHeaderLength= 6 + 2 * noComponentsInScan;
                dataOutputStream.writeShort(JpegMarker.SOS);
                dataOutputStream.writeShort(scanHeaderLength);
                dataOutputStream.writeByte(noComponentsInScan);
                for (var component: scan.components()){
                    var packedDcAndAcEntropyEncodingTableIdentifier= component.dcEntropyEncodingTable().tableDestinationIdentifier().val()<<4| component.acEntropyEncodingTable().tableDestinationIdentifier().val();
                    dataOutputStream.writeByte(component.label());
                    dataOutputStream.write(packedDcAndAcEntropyEncodingTableIdentifier);
                }
                dataOutputStream.writeByte(scan.startOfSpectralSelect());
                dataOutputStream.writeByte(scan.endOfSpectralSelect());
                var successiveApproximationHighAndLow=
                        scan.successiveApproximationBitPositionHigh()<<4|scan.successiveApproximationBitPositionLow();
                dataOutputStream.writeByte(successiveApproximationHighAndLow);
                var imageDataArray=scan.EntropyEncodedSegment().toArray(new Byte[0]);
                dataOutputStream.write(ArrayUtils.toPrimitive(imageDataArray));
            }
            dataOutputStream.writeShort(JpegMarker.EOI);
            byteArrayOutputStream.writeTo(new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void  writeBytes(DataOutputStream dataOutputStream, int [] array) throws IOException {
        for (int j : array) {
            dataOutputStream.writeByte(j);
        }
    }
}
