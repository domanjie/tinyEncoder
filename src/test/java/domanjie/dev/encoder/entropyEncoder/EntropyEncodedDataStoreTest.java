package domanjie.dev.encoder.entropyEncoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntropyEncodedDataStoreTest {

    @Test
    public void addingValOfSpecifiedBitSizeShouldResultACorrectlyPaddedArrayCase1(){
        var arrayPacker=new EntropyEncodedDataStore();
        arrayPacker.addBits(0b111, 5);
        var imageData= arrayPacker.getData();
        assertEquals(1, imageData.size());
        assertEquals((byte) 0b0011_1111, imageData.get(0));
    }
    @Test
    public void addingValOfSpecifiedBitSizeShouldResultInACorrectlyPaddedArrayCase2(){
        var arrayPacker=new EntropyEncodedDataStore();
        arrayPacker.addBits(0b111, 5);
        arrayPacker.addBits(0b1, 9);
        var byteArr= arrayPacker.getData();
        assertEquals(2, byteArr.size());
        assertEquals((byte) 0b0011_1000,byteArr.get(0) );
        assertEquals((byte) 0b0000_0111, byteArr.get(1));
    }
    @Test
    public void addingValOfSpecifiedBitSizeShouldResultInACorrectlyPaddedArrayWithStuffingWhereNecessary(){
        var arrayPacker=new EntropyEncodedDataStore();
        arrayPacker.addBits(0b1, 9);
        var byteArr= arrayPacker.getData();
        assertEquals(3, byteArr.size());
        assertEquals((byte) 0x0,byteArr.get(0));
        assertEquals((byte) 0b1111_1111, byteArr.get(1));
        assertEquals((byte) 0b0000_0000, byteArr.get(2));
    }
    @Test
    public void addingValOfSpecifiedBitSizeShouldResultInACorrectlyPaddedArrayWithStuffingWhereNecessaryCase2(){
        var arrayPacker=new EntropyEncodedDataStore();
        arrayPacker.addBits(-1, 9);
        var byteArr= arrayPacker.getData();
        assertEquals(4, byteArr.size());
        assertEquals((byte) 0b1111_1111,byteArr.get(0));
        assertEquals((byte) 0b0000_0000, byteArr.get(1));
        assertEquals((byte) 0b1111_1111, byteArr.get(2));
        assertEquals((byte) 0b0000_0000, byteArr.get(1));
    }

}