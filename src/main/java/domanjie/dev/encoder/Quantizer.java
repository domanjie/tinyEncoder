package domanjie.dev.encoder;

public class Quantizer {
    public  static   int [][] quantize(int [][] quantTable,  int  [][] inputArr ) {

        if (isNon8x8(quantTable) && isNon8x8(inputArr))
            throw new IllegalArgumentException("quant table and input block must be an 8x8 array");
        else if (isNon8x8(quantTable))
            throw new IllegalArgumentException("quant table must be an 8x8 array");
        else if (isNon8x8(inputArr))
            throw new IllegalArgumentException("input block must be an 8x8 array");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                inputArr[x][y] = Math.round((float) inputArr[x][y] / quantTable[x][y]);
            }
        }
        return inputArr ;

    }
    private static boolean isNon8x8( int  [][] inputArr ){
        return (inputArr.length != 8 || inputArr[0].length != 8);
    }
}
