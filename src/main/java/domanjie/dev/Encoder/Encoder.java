package domanjie.dev.Encoder;

import domanjie.dev.bmp.Bitmap;

import java.util.ArrayList;

public class Encoder {

    private final ArrayList<Byte> entropyCodedPayload= new ArrayList<>();
    private final Quantizer quantizer;
    private Encoder(Quantizer quantizer){
        this .quantizer=quantizer;
    }

    public  void encode(Bitmap bitmapImg){


        int [][] components= getYCbCrComponents(bitmapImg);
        downsample();
        forwardDct();
        quantize();
        encodeEntropy();
    }

    private int[][] getYCbCrComponents(Bitmap bitmapImg) {
//        bitmapImg.get
        return null;
    }

    private Bitmap downsample(){
        return null;

    }

    private Bitmap forwardDct(){
        return null;

    }
    private  Bitmap quantize(){
        return null;

    }
    private Bitmap encodeEntropy(){
        return null;

    }

    public Quantizer getQuantizer() {
        return quantizer;
    }

    public ArrayList<Byte> getEntropyCodedPayload() {
        return entropyCodedPayload;
    }
}
