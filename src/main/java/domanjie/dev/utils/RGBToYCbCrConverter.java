package domanjie.dev.utils;

public class RGBToYCbCrConverter {
    public static int[][][] convert(int[][] pixelArray){
        var y=new int[pixelArray.length][pixelArray[0].length];
        var cb=new int[pixelArray.length][pixelArray[0].length];
        var cr=new int[pixelArray.length][pixelArray[0].length];
        for(int row =0; row<pixelArray.length;row++){
            for (int col=0 ;col <pixelArray[0].length; col++){
                var packedPixel=pixelArray[row][col];
                var red  = packedPixel>>16 & 0xff;
                var green= packedPixel>>8& 0xff;
                var blue = packedPixel&0xff;
                 y[row][col]= (int)  Math.round(0.299*red  + 0.587*green + 0.114*blue);
                cb[row][col]= (int) (Math.round(-0.1687*red  -  0.3313*green +0.5* blue )+128);
                cr[row][col]= (int) (Math.round(0.5*red- 0.4187*green - 0.0813 *blue) +128);
            }
        }
        return new int[][][]{y, cb , cr};
    }

}
