package domanjie.dev.encoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantizerTest {
    @Test
    void expectedArrayShouldBeReturnedAfterQuantization(){
        var quantTable=new _8x8Block(
                new int[][]{
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                        {2,2,2,2,2,2,2,2},
                }
        );
        var inputBlock=new _8x8Block(new int[][]{
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
                {2,2,2,2,2,2,2,2},
        });
        var expectedOutputBlock= new _8x8Block(
                new int[][]{
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                        {1,1,1,1,1,1,1,1},
                }
        );
        var actualOutputBlock=Quantizer.quantize(quantTable, inputBlock);
        assertEquals(expectedOutputBlock, actualOutputBlock);

    }
    @Test
    void expectedArrayShouldBeReturnedAfterQuantization2(){
        var quantTable= new _8x8Block(new int[][]{
                {16,11,10,16,24,40,51,61},
                {12,12,14,19,26,58,60,55},
                {14,13,16,24,40,57,69,56},
                {14,17,22,29,51,87,80,62},
                {18,22,37,56,68,109,103,77},
                {24,35,55,64,81,104,113,192},
                {49,64,78,87,103,121,120,101},
                {72,92,95,98,112,100,103,99},
        });
        var inputBlock=new  _8x8Block( new int[][]{
                {67,122,2,23,41,0,89,1},
                {2,25,32,52,2,2,2,23},
                {22,34,21,65,23,69,2,2},
                {74,81,8,2,67,62,45,0},
                {21,90,21,0,24,25,3,2},
                {23,2,2,2,27,56,26,89},
                {90,78,88,245,5,243,32,2},
                {42,12,212,21,212,242,23,23},
        });
        var expectedOutputBlock =new _8x8Block( new int[][]{
                {4,11,0,1,2,0,2,0},
                {0,2,2,3,0,0,0,0},
                {2,3,1,3,1,1,0,0},
                {5,5,0,0,1,1,1,0},
                {1,4,1,0,0,0,0,0},
                {1,0,0,0,0,1,0,0},
                {2,1,1,3,0,2,0,0},
                {1,0,2,0,2,2,0,0},
        });
        var actualOutputBlock=Quantizer.quantize(quantTable, inputBlock);
        assertEquals(expectedOutputBlock, actualOutputBlock);
    }
}