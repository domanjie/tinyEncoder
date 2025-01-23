package domanjie.dev.utils;

import org.apache.commons.math3.linear.MatrixUtils;

public class TwoDIntArrayUtils {

    public static int  [][] getSubMatrix(final int startRow, final int endRow,
                                           final int startColumn, final int endColumn,   int[][]  inputArr){
        var maxRowIndexInputArr=inputArr.length-1;
        var maxColIndexInputArr=inputArr[0].length-1;

        checkRowBounds(startRow, maxRowIndexInputArr);
        checkRowBounds(endRow, maxRowIndexInputArr);

        checkColBounds(startColumn,maxColIndexInputArr);
        checkColBounds(endColumn, maxColIndexInputArr);

        var nCol=endColumn-startColumn+1;
        var nRow=endRow-startRow+1;


        var  result =new int[nRow][nCol];
        for(int rowIndex=startRow;rowIndex<=endRow;rowIndex++ ){
            System.arraycopy(inputArr[rowIndex], startColumn, result[rowIndex - startRow], 0, nCol);
        }
        return  result;
    }

    private static void checkColBounds(int index,int maxColIndex){
        if(index<0||index>maxColIndex){
            throw new OutOfBoundsException("column index: "+index+ " out of  bounds for input column range 0-"+maxColIndex);
        }
    }

    private static void checkRowBounds(int index, int maxRowIndex) {
        if(index<0||index>maxRowIndex){
            throw new OutOfBoundsException("row index: "+index+ " out of  bounds for input row range 0-"+maxRowIndex);
        }
    }


    public static void setSubMatrix(int startRow, int startColumn, int [][] subMatrix, int [][] mainMatrix){


        var NRowsSub=subMatrix.length;
        var NColsSub=subMatrix[0].length;

        var NRowsMain=mainMatrix.length;
        var NColsMain=mainMatrix[0].length;

        checkRowBounds(startRow, NRowsMain-1);
        checkColBounds(startColumn,NColsMain-1);;

        if(startRow+NRowsSub>NRowsMain|| startColumn+NColsSub>NColsMain)
            throw new OutOfBoundsException("sub-matrix too large to fit from  main-matrix["+startRow+"]["+startColumn+"]");

        for(int i = 0; i<NRowsSub;i++ ){
            System.arraycopy(subMatrix[i], 0, mainMatrix[startRow + i], startColumn, NColsSub);
        }
    }

    public static int[][]  transposeSquareMatrix(int [][] m ) {
        int [][] temp = new int [m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

}
