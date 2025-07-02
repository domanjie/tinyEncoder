package domanjie.dev.encoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _8x8BlockTest {
    @Test
    void callingWithNon8x8InputBlockShouldThrowIllegalArgumentException(){
        var val=new int[9][3];

        var expectedException = assertThrows(IllegalArgumentException.class , ()->{
            new _8x8Block(val);
        });
        assertEquals("input must be an 8x8 array", expectedException.getMessage());
    }
    @Test
    void callingWithNon8x8QuantTableAndInputBlockShouldThrowIllegalArgumentException(){
        var val=new int[7][7];
        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class,()->{
            new _8x8Block(val);
        });
        assertEquals("input must be an 8x8 array", exception.getMessage());
    }
}