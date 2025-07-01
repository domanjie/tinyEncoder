package domanjie.dev.encoder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TwoDDctTransformerTest {

    @Test
    public void shouldReturn2dForwardDCTTestCase1(){
        //arrange
        var input=new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        };
        var expectedOutput=new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
        };
        var actualOutput= TwoDDctTransformer.twoDimensionalForwardDct(input);
        assertArrayEquals(expectedOutput, actualOutput);
    }
    @Test
    public void shouldReturn2dForwardDCTTestCase2(){
       //arrange
        var input=new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
        };
        var expectedOutput=new int[][]{
                { 8, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
        };
        var actualOutput= TwoDDctTransformer.twoDimensionalForwardDct(input);
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void shouldReturn2dForwardDCTTestCase3(){
        //arrange
        var input=new int[][]{
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4, 4, 4},
        };
        var expectedOutput=new int[][]{
                {32, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
                { 0, 0, 0, 0, 0, 0, 0, 0,},
        };
        var actualOutput= TwoDDctTransformer.twoDimensionalForwardDct(input);
        assertArrayEquals(expectedOutput, actualOutput);
    }
}
