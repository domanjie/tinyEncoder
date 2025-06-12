package domanjie.dev.utils;

import org.apache.commons.math3.linear.MatrixUtils;

public class TwoDIntArrayUtils {


    //return sub-matrix from rowStart -> rowEnd(exclusive) and columnStart -> columnEnd(exclusive)
    public static int  [][] getSubMatrix(final int rowStart, final int rowEnd,
                                           final int columnStart, final int columnEnd,   int[][]  inputArr){

        checkRowBounds(rowStart, inputArr.length);
        checkRowBounds(rowEnd, inputArr.length);

        checkColBounds(columnStart,inputArr[0].length);
        checkColBounds(columnEnd,inputArr[0].length);

        var nCol=columnEnd-columnStart;
        var nRow=rowEnd-rowStart;


        var  result =new int[nRow][nCol];
        for(int rowIndex=rowStart;rowIndex<rowEnd;rowIndex++ ){
            System.arraycopy(inputArr[rowIndex], columnStart, result[rowIndex - rowStart], 0, nCol);
        }
        return  result;
    }

    private static void checkColBounds(int index,int maxColEnd){
        if(index<0||index>maxColEnd){
            throw new OutOfBoundsException("column index: "+index+ " out of  bounds for input column range 0-"+(maxColEnd-1));
        }
    }

    private static void checkRowBounds(int index, int maxRowEnd) {
        if(index<0||index>maxRowEnd){
            throw new OutOfBoundsException("row index: "+index+ " out of  bounds for input row range 0-"+(maxRowEnd-1));
        }
    }


    public static void setSubMatrix(int rowStart, int columnStart, int [][] subMatrix, int [][] mainMatrix){


        var NRowsSub=subMatrix.length;
        var NColsSub=subMatrix[0].length;

        var NRowsMain=mainMatrix.length;
        var NColsMain=mainMatrix[0].length;

        checkRowBounds(rowStart, NRowsMain-1);
        checkColBounds(columnStart,NColsMain-1);;

        if(rowStart+NRowsSub>NRowsMain|| columnStart+NColsSub>NColsMain)
            throw new OutOfBoundsException("sub-matrix too large to fit from  main-matrix["+rowStart+"]["+columnStart+"]");

        for(int i = 0; i<NRowsSub;i++ ){
            System.arraycopy(subMatrix[i], 0, mainMatrix[rowStart + i], columnStart, NColsSub);
        }
    }

    public static int[][]  transposeSquareMatrix(int [][] m ) {
        int [][] temp = new int [m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
    public static  int[][] padColumn(int[][] inputArr, int padBy) {
        if (padBy< 0){
            throw new IllegalArgumentException("Cannot pad Column by negative value");
        }
        var newNoCols=inputArr[0].length + padBy;
        var outputArr=new int[inputArr.length][newNoCols];
        for(int i =0 ; i < inputArr.length; i++){
            System.arraycopy(inputArr[i],0,outputArr[i], 0, inputArr[i].length);
            var lastColVal=inputArr[i][inputArr[i].length-1];
            for(int j =inputArr[i].length ;j<newNoCols; j++){
                outputArr[i][j]= lastColVal;
            }
        }
        return outputArr;
    }
    public  static   int[][] padRow(int [][] inputArr, int padBy) {
        if (padBy< 0){
            throw new IllegalArgumentException("Cannot pad Row by negative value");
        }
        var newNoRows= inputArr.length+padBy;
        var outputArr=new int[newNoRows][inputArr[0].length];
        System.arraycopy(inputArr, 0,outputArr,0,inputArr.length);
        var lastRow=inputArr[inputArr.length-1];
        for(int j=inputArr.length; j<newNoRows;j++){
            outputArr[j]=lastRow.clone();
        }
        return outputArr;
    }
}
