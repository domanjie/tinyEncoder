package domanjie.dev.Encoder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoWayDctTransformerTest {


    @Test
    public void shouldReturn1dForward(){
        var inputArr=new int[]{-12, 12,1, 2};
        var expectedArray=new int[]{6,-17,-33,-31};
        var actualArray =TwoWayDctTransformer.oneDimensionalForwardFct(inputArr);
        assertArrayEquals(expectedArray, actualArray);
    }
    @Test
    public void shouldReturn1dForward2(){
        var inputArr=new int[]{128, 12, -55, 112, 100,-125, 12, -3};
        var expectedOutput=new int[]{362,339,-5,67,697,193,-444,144};
        var actualOutput =TwoWayDctTransformer.oneDimensionalForwardFct(inputArr);
        assertArrayEquals(expectedOutput,actualOutput);
    }

    @Test
    public void shouldReturn2dDForwardDctOfInputArray(){
        var inputArr=new int[][]{{-12, 12},
                                 { 12, 23}};
        var expectedOutput=new int[][]{{ 140, -100},
                                       {-99, -25}};
        var actualOutput= TwoWayDctTransformer.twoDimensionalForwardDct(inputArr);
        assertArrayEquals(expectedOutput, actualOutput);
    }
//    @Test
//    public void shouldReturn2dDForwardDctOfInputArray2(){
//        var inputArr=new int[][]{{-12, 12,1, 2}, {12, 23,1, -3,}, {4,-12, 4,9}, {4,-12, 2,9}};
//        TwoWayDctTransformer.twoDimensionalForwardDct(inputArr);
//    }
    @Test
    public void shouldReturn2dDctOfInputArray3(){
        //arrange
        var inputArr=new int[][]{{-12, 12,1, 2,4,-12, 2,9}, {12, 23,1, -3,4,-12, 4,9}, {}};
        TwoWayDctTransformer.twoDimensionalForwardDct(inputArr);
    }
}
