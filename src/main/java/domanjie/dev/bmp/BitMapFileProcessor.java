package domanjie.dev.bmp;

import domanjie.dev.DIBHeader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public  class BitMapFileProcessor {

    private static  final int OFFSET_TO_PIXEL_ARRAY_OFFSET=10;
    private static final int OFFSET_TO_IMAGE_WIDTH=18;
    private static final int OFFSET_TO_IMAGE_HEIGHT=22;
    private static final int OFFSET_TO_BPP=28;

    //converts a byteArr to an internal bitmap representation
    public static Bitmap bytesToBmp(byte[] byteArr){
        var pixelArrayOffset =getBytes(byteArr ,OFFSET_TO_PIXEL_ARRAY_OFFSET ,4).getInt();
        var imageWidth= getBytes(byteArr, OFFSET_TO_IMAGE_WIDTH,4 ).getInt();
        var imageHeight=getBytes(byteArr,OFFSET_TO_IMAGE_HEIGHT,4).getInt();
        var bitsPerPixel=getBytes(byteArr, OFFSET_TO_BPP, 2).getShort();
        var RowSize=Math.ceil(((double)bitsPerPixel * (double)imageWidth)/32)*4;
        var  bytePerPixel=bitsPerPixel/8;
        var absImageHeight=Math.abs(imageHeight);
        var pixelArray=new int [absImageHeight][imageWidth];

        //loops through all rows gets each pixel(an array with size ==bpp) and insert it in a 2d array
        for(int pixelY=absImageHeight-1;pixelY>=0 ;pixelY--){
            for(int pixelX=0 ;pixelX<imageWidth ;pixelX++){
                int pixelStartIndex= (int) (pixelArrayOffset+(pixelY*RowSize)+(pixelX*bytePerPixel));

                var packedPixel=0;

                for(int i =0; i <bytePerPixel; i++){
                    packedPixel|=(byteArr[pixelStartIndex+i]&0xff)<<(24-8*i);
                }

                pixelArray[pixelY][pixelX]=packedPixel;
            }
        }
        return new Bitmap(new DIBHeader(imageHeight,imageWidth,  bitsPerPixel,Compression.BI_RGB),pixelArray);
    }

    //returns a specific no of bytes from byte array starting from a particular offset
    public static ByteBuffer getBytes(byte[] srcArr, int offset, int noOfBytes){
        byte[] byteArr=new byte[noOfBytes];
        System.arraycopy(srcArr, offset, byteArr, 0, noOfBytes);
        return ByteBuffer.wrap(byteArr).order(ByteOrder.LITTLE_ENDIAN);
    }
}
