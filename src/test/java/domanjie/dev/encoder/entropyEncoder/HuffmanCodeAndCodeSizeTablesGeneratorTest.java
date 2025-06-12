package domanjie.dev.encoder.entropyEncoder;

import domanjie.dev.encoder.entropyEncoder.HuffmanCodeAndCodeSizeTablesGenerator;
import org.junit.jupiter.api.Test;

import java.util.Map;
import static java.util.Map.*;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodeAndCodeSizeTablesGeneratorTest {
    @Test
    public void shouldReturnExpectedHuffCodeAndHuffCodeSizeTables(){
        var bitList=new int[]{  0, 1, 3, 5,0, 0, 0, 0,0, 0, 0, 0,0, 0, 0, 0};
        var HuffVal=new int[]{0x08, 0x00, 0x07,0xb8, 0x09, 0x38,0x39, 0x76,0x78};
        var generator=new HuffmanCodeAndCodeSizeTablesGenerator(bitList,HuffVal);
        Map<Integer, Integer> expectedHuffmanCodeTable= Map.ofEntries(
                entry(0x08,0b00),
                entry(0x00,0b010),
                entry(0x07,0b011),
                entry(0xb8,0b100),
                entry(0x09,0b1010),
                entry(0x38,0b1011),
                entry(0x39,0b1100),
                entry(0x76,0b1101),
                entry(0x78,0b1110)
        );
        Map<Integer , Integer> expectedHuffmanCodeSizeTable=Map.ofEntries(
                entry(0x08,2),
                entry(0x00,3),
                entry(0x07,3),
                entry(0xb8,3),
                entry(0x09,4),
                entry(0x38,4),
                entry(0x39,4),
                entry(0x76,4),
                entry(0x78,4)
        );
        assertEquals(expectedHuffmanCodeTable,generator.getHuffmanCodeTable());
        assertEquals(expectedHuffmanCodeSizeTable,generator.getHuffmanCodeSizeTable());
    }
    @Test
    public void shouldReturnExpectedHuffCodeAndHuffCodeSizeTablesCase2(){
        var bitList=new int[]{ 1, 0, 2, 3,0, 0, 0, 0,0, 0, 0, 0,0, 0, 0, 0};
        var huffVal=new int[]{ 0x00, 0x06, 0x08,0x38, 0x88, 0xb6};
        var generator=new HuffmanCodeAndCodeSizeTablesGenerator(bitList,huffVal);
        Map<Integer, Integer> expectedHuffmanCodeTable= Map.ofEntries(
                entry(0x00, 0b0),
                entry(0x06, 0b100),
                entry(0x08, 0b101),
                entry(0x38, 0b1100),
                entry(0x88, 0b1101),
                entry(0xb6, 0b1110)
        );
        Map<Integer , Integer> expectedHuffmanCodeSizeTable=Map.ofEntries(
                entry(0x00, 1),
                entry(0x06, 3),
                entry(0x08, 3),
                entry(0x38, 4),
                entry(0x88, 4),
                entry(0xb6, 4)
        );
        assertEquals(expectedHuffmanCodeTable,generator.getHuffmanCodeTable());
        assertEquals(expectedHuffmanCodeSizeTable,generator.getHuffmanCodeSizeTable());
    }
    @Test
    public void shouldReturnExpectedHuffCodeForTheInputSymbol(){
        var bitList=new int[]{ 1, 0, 2, 3,0, 0, 0, 0,0, 0, 0, 0,0, 0, 0, 0};
        var huffVal=new int[]{ 0x00, 0x06, 0x08,0x38, 0x88, 0xb6};
        var generator=new HuffmanCodeAndCodeSizeTablesGenerator(bitList,huffVal);
        assertEquals(0b1101, generator.getHuffmanCodeForSymbol(0x88));
    }
    @Test
    public void shouldReturnExpectedHuffSizeForTheInputSymbol(){
        var bitList=new int[]{ 1, 0, 2, 3,0, 0, 0, 0,0, 0, 0, 0,0, 0, 0, 0};
        var huffVal=new int[]{ 0x00, 0x06, 0x08,0x38, 0x88, 0xb6};
        var generator=new HuffmanCodeAndCodeSizeTablesGenerator(bitList,huffVal);
        assertEquals(4, generator.getHuffmanCodeSizeForSymbol(0x88));
    }



    @Test
    public void shouldReturnExpectedHuffCodeAndHuffCodeSizeTablesCase3(){

                var bitList=new int[]{
                0x00, 0x01, 0x05, 0x01,
                0x01, 0x01, 0x01, 0x01,
                0x01, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00
        };
       var huffVal= new int[]{
                0x00, 0x01, 0x02, 0x03,
                0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0A, 0x0B
       };
        Map<Integer, Integer> expectedHuffmanCodeTable= Map.ofEntries(
                entry(0,0b00),
                entry(1,0b010),
                entry(2,0b011),
                entry(3,0b100),
                entry(4,0b101),
                entry(5,0b110),
                entry(6,0b1110),
                entry(7,0b11110),
                entry(8,0b111110),
                entry(9,0b1111110),
                entry(10,0b11111110),
                entry(11,0b111111110)

        );
        Map<Integer, Integer> expectedHuffmanCodeSizeTable= Map.ofEntries(
                entry(0,2),
                entry(1,3),
                entry(2,3),
                entry(3,3),
                entry(4,3),
                entry(5,3),
                entry(6,4),
                entry(7,5),
                entry(8,6),
                entry(9,7),
                entry(10,8),
                entry(11,9)

        );
       var generator= new HuffmanCodeAndCodeSizeTablesGenerator(bitList, huffVal);
       assertEquals(expectedHuffmanCodeTable,generator.getHuffmanCodeTable());
       assertEquals(expectedHuffmanCodeSizeTable,generator.getHuffmanCodeSizeTable());
    }
}