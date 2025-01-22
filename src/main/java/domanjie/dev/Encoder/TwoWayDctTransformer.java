package domanjie.dev.Encoder;

import domanjie.dev.utils.TwoDIntArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.Arrays;

public class TwoWayDctTransformer {


    //performs a 2d  dct(type 2) on an array whose dimension is greater than  2
    public static  int [][] twoDimensionalForwardDct(int[][] arrayIn){
        //perform 1d dct on all rows of array
        var result=new int [arrayIn.length][arrayIn[0].length];
        for(int i=0;i<arrayIn.length;i++){
            result[i]=oneDimensionalForwardFct(arrayIn[i]);
        }
        result =TwoDIntArrayUtils.transposeSquareMatrix(result);
        for(int i=0;i<arrayIn.length;i++){
            result[i]=oneDimensionalForwardFct(result[i]);
        }
        result =TwoDIntArrayUtils.transposeSquareMatrix(result);
        return result;

    }


    public static int[] oneDimensionalForwardFct(int[] arrayIn){
        var  N=arrayIn.length;
        //prepare input for fft.
        double [] fftInput=new double[4*N];

        //distribute input values at odd indexes of fft input(required set-up for performing dct TYPE 2 with fft)
        for(int i=0;i<N;i++){
            fftInput[4*N-(2*i+1)]= fftInput[2*i+1]=arrayIn[i];
        }
        FastFourierTransformer fastFourierTransformer=new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftOutput =fastFourierTransformer.transform(fftInput , TransformType.FORWARD);

        return Arrays.stream(fftOutput).limit(N)
                .mapToInt(complex->
                        (int)Math.round(complex.getReal())
        ).toArray();
    }
}
