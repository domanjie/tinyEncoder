package domanjie.dev.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoDIntArrayUtilsTest {
    @Test
    public void shouldReturnTransposeOfInputArray(){
        var inputArray =new   int[][]{{12, 23, 145,  6},
                                      { 6, 32,  12,  6},
                                      { 9, 32,  12, 23},
                                      { 6,  6,  17, 83}};
        var expectedOutput=new int[][]{{12,6,9,6}, {23,32,32,6}, {145,12,12,17}, {6,6,23,83}};

        var actualOutput  =TwoDIntArrayUtils.transposeSquareMatrix(inputArray);
        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    public void shouldReturnTransposeOfInputArray2(){
        var inputArray =new int[][]{{ 7,   6},
                                    {34, -12},
                                    {67,  -1},
                                    { 0,   1}
        };
        var expectedOutput=new int[][]{{7, 34, 67, 0},
                                       {6,-12, -1, 1}
        };
        var actualOutput=TwoDIntArrayUtils.transposeSquareMatrix(inputArray);
        assertArrayEquals(expectedOutput, actualOutput);
    }
    @Test
    public void shouldReturnSubMatrixFromSpecifiedStartToEndDimensions(){
        var inputArray =new int[][]{{ 7,   6},
                {34, -12},
                {67,  -1},
                { 0,   1}
        };
        var actualOutput=TwoDIntArrayUtils.getSubMatrix(0, 4, 0,2, inputArray);
        //expected output is input arr
        assertArrayEquals(inputArray ,actualOutput);
    };
    @Test
    public void shouldReturnSubMatrixFromSpecifiedStartToEndDimensions2(){
        var inputArray =new int[][]{
                { 7,   6,  8, 21},
                { 0, -12, 46, 81},
                { 3,  -1, 50,  1},
                { 0,   1, 91, 32}
        };
        var expectedOutput=new int[][]{{50 ,  1},
                                       {91,  32}
        };
        var actualOutput=TwoDIntArrayUtils.getSubMatrix(2, 4, 2,4, inputArray);
        assertArrayEquals(expectedOutput, actualOutput );
    };
    @Test
    public void shouldThrowOutOfBoundExceptionWhenStartRowIsOutOfRange(){
        var inputArray =new int[][]{
                { 7,   6},
                { 0, -12},
        };
        var exception= assertThrows(OutOfBoundsException.class,()->{
                TwoDIntArrayUtils.getSubMatrix(-2, 3, 0,1, inputArray);
        });
        var expectedExceptionMessage="row index: -2 out of  bounds for input row range 0-1";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    };
    @Test
    public void shouldThrowOutOfBoundExceptionWhenEndRowIsOutOfRange(){
        var inputArray =new int[][]{
                { 7,   6},
                { 0, -12},
        };
        var exception= assertThrows(OutOfBoundsException.class,()->{
            TwoDIntArrayUtils.getSubMatrix(0, -4, 0,1, inputArray);
        });
        var expectedExceptionMessage="row index: -4 out of  bounds for input row range 0-1";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    };
    @Test
    public void shouldThrowOutOfBoundExceptionWhenStartColumnIsOutOfRange(){
        var inputArray =new int[][]{
                { 7,   6},
                { 0, -12},
        };
        var exception= assertThrows(OutOfBoundsException.class,()->{
            TwoDIntArrayUtils.getSubMatrix(0, 1, 5,1, inputArray);
        });
        var expectedExceptionMessage="column index: 5 out of  bounds for input column range 0-1";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    };
    @Test
    public void shouldThrowOutOfBoundExceptionWhenEndColumnIsOutOfRange(){
        var inputArray =new int[][]{
                { 7,   6},
                { 0, -12},
        };
        var exception= assertThrows(OutOfBoundsException.class,()->{
            TwoDIntArrayUtils.getSubMatrix(0, 1, 0,-5, inputArray);
        });
        var expectedExceptionMessage="column index: -5 out of  bounds for input column range 0-1";
        assertEquals(expectedExceptionMessage, exception.getMessage());
    };

    @Test
    public void shouldSetSubMatrixIntoMainMatrix(){
        var subMatrix=new int[][]{
                { 7,   6},
                { 0, -12},
        };
        var mainMatrix=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1}
        };
        var expectedOutput=new int[][]{
                { 6, 12,  12,  4, 90},
                {87,  7,   6, 89, 12},
                {32,  0, -12,  1,  0},
                { 4, 67,   9, 12,  1}
        };

        TwoDIntArrayUtils.setSubMatrix(1,1 ,subMatrix, mainMatrix);
        assertArrayEquals(expectedOutput,mainMatrix);
    };

    @Test
    public void shouldSetSubMatrixIntoMainMatrix2(){
        var subMatrix=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
        };
        var mainMatrix=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1}
        };
        var expectedOutput=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12,  7,  6, 12},
                { 4, 67,  0,-12, 12}
        };

        TwoDIntArrayUtils.setSubMatrix(2,2,subMatrix, mainMatrix);
        assertArrayEquals(expectedOutput,mainMatrix);
    }


    @Test
    public void shouldThrowOutOfBoundsExceptionWhenStartRowIsOutOfRangeException(){
        var subMatrix=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
        };
        var mainMatrix=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1}
        };
        var expectedOutput=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12,  7,  6, 12},
                { 4, 67,  0,-12, 12}
        };
        assertThrows(OutOfBoundsException.class,()->{
            TwoDIntArrayUtils.setSubMatrix(-2, 2, subMatrix, mainMatrix);
        });

    }
    @Test
    public void shouldTrowOutOfBoundExceptionWhenSubMatrixDoesNotFitFromSpecifiedStartDimensions(){
        var subMatrix=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
        };
        var mainMatrix=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1}
        };
        var expectedOutput=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12,  7,  6, 12},
                { 4, 67,  0,-12, 12}
        };
        var startRow =3;
        var startColumn=3;
        var exception= assertThrows(OutOfBoundsException.class,()->{
            TwoDIntArrayUtils.setSubMatrix(startRow,startColumn, subMatrix, mainMatrix);
        });
        var expectedExceptionMessage="sub-matrix too large to fit from  main-matrix["+startRow+"]["+startColumn+"]";
        assertEquals(expectedExceptionMessage,exception.getMessage());
    }

    @Test
    public void shouldReturnExpectedColumnPaddedArrayCase1(){
        var arr=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
        };
        var expectedOutput=new int[][]{
                { 7,   6, 12, 12, 12, 12},
                { 0, -12, 12, 12,  12, 12},
        };
        var actualOutput= TwoDIntArrayUtils.padColumn(arr, 3);
        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    public void shouldReturnExpectedColumnPaddedArrayCase2(){
        var inputArr=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1}
        };
        var expectedOutput=new int[][]{
                { 6, 12, 12,  4, 90, 90},
                {87, 53, 45, 89, 12, 12},
                {32, 12, 12,  1,  0,  0},
                { 4, 67,  9, 12,  1,  1}

        };

        var actualOutput= TwoDIntArrayUtils.padColumn(inputArr, 1);
        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    public void shouldReturnExpectedRowPaddedArrayCase1(){
        var inputArr=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1}
        };
        var expectedOutput=new int[][]{
                { 6, 12, 12,  4, 90},
                {87, 53, 45, 89, 12},
                {32, 12, 12,  1,  0},
                { 4, 67,  9, 12,  1},
                { 4, 67,  9, 12,  1},
        };
        var actualOutput= TwoDIntArrayUtils.padRow (inputArr, 1);
        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    public void shouldReturnExpectedRowPaddedArrayCase2(){
        var arr=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
        };
        var expectedOutput=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
                { 0, -12, 12},
                { 0, -12, 12},
                { 0, -12, 12},
        };
        var actualOutput= TwoDIntArrayUtils.padRow(arr, 3);
        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    public void shouldReturnExpectedColumnAndRowPaddedArrayCase1(){
        var arr=new int[][]{
                { 7,   6, 12},
                { 0, -12, 12},
        };
        var expectedOutput=new int[][]{
                { 7,   6, 12, 12, 12},
                { 0, -12, 12, 12, 12},
                { 0, -12, 12, 12, 12},
                { 0, -12, 12, 12, 12},
                { 0, -12, 12, 12, 12},
        };
        var intermediateOutput= TwoDIntArrayUtils.padRow(arr, 3);
        var actualOutput= TwoDIntArrayUtils.padColumn(intermediateOutput, 2 );
        assertArrayEquals(expectedOutput,actualOutput);
    }
    @Test
    public void shouldReturnExpectedColumnAndRowPaddedArrayCase2(){
        var arr=new int[][]{
                {0}
        };
        var expectedOutput=new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
        };
        var intermediateOutput= TwoDIntArrayUtils.padRow(arr, 7);
        var actualOutput= TwoDIntArrayUtils.padColumn(intermediateOutput, 7 );
        assertArrayEquals(expectedOutput,actualOutput);
    }
}