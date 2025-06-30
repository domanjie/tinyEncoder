package domanjie.dev.bmp;

import domanjie.dev.DIBHeader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class BitMapFileProcessorTest {


//    @Test
//    void shouldReturnExpectedBitmapObj(){
//        //Arrange
//        byte[] validBitmapByteArr={66, 77, -118, 0, 0, 0, 0, 0, 0, 0, 122, 0,
//                0, 0, 108, 0, 0, 0, 2, 0, 0, 0, -2, -1, -1, -1, 1, 0, 32, 0,
//                3, 0, 0, 0, 16, 0, 0, 0, 19, 11, 0, 0, 19, 11, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0,
//                -1, 32, 110, 105, 87, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -128, 0, -1, 0, 0, -1, -1,
//                -1, -1, -1, -1, -1, 0, 0, -1};
//
////        var expectedPixelArray=new int[][]{{  {0, -128, 0, -1}, {0, 0, -1, -1}}, {{-1, -1, -1, -1}, {-1, 0, 0, -1}}};
//        var expectedPixelArray=new int[][]{{8388863, 65535}, {-1, -16776961}};
//        var expectedBitmapObj=new Bitmap (new DIBHeader(-2,2, (short) 32, Compression.BI_RGB),expectedPixelArray);
//
//        //Act
//        var actualBitmapObj= BitMapFileProcessor.bytesToBmp(validBitmapByteArr);
//
//        //assert
//        assertEquals(expectedBitmapObj, actualBitmapObj );
//    }
}