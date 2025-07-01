package domanjie.dev.encoder;

public class TwoWayDctTransformer {
    private static final double[][] precomputedCosines;
    static {
        precomputedCosines= new double[8][8];
        for(int freq=0; freq <8; freq++ ){
            for(int x=0; x<8; x++)
                precomputedCosines[freq][x]= Math.cos(((2*x+1)*freq*Math.PI)/16);
        }
    }

    //performs a 2d  dct(type 2) on an array whose dimension is greater than  2
    public static  int [][] twoDimensionalForwardDct(int[][] matrix) {
        int n = matrix[0].length, m = matrix.length;
        int[][] dct = new int[m][n];
        double cu, cv;
        for (int u = 0; u < m; u++) {
            for (int v = 0; v < n; v++) {
                if (u == 0)
                    cu = 1 / Math.sqrt(m);
                else
                    cu = Math.sqrt(2.0 / m);

                if (v == 0)
                    cv = 1 / Math.sqrt(n);
                else
                    cv = Math.sqrt(2.0 / n);

                double sum = 0;
                for (int x = 0; x < m; x++) {
                    for (int y = 0; y < n; y++)
                        sum += matrix[x][y] * precomputedCosines[u][x] * precomputedCosines[v][y];
                }
                dct[u][v] = (int) Math.round(cu * cv * sum);
            }
        }
        return dct;

    }


//    public static int[] oneDimensionalForwardFct(int[] arrayIn){
//        var  N=arrayIn.length;
//        //prepare input for fft.
//        double [] fftInput=new double[4*N];
//
//        //distribute input values at odd indexes of fft input(required set-up for performing dct TYPE 2 with fft)
//        for(int i=0;i<N;i++){
//            fftInput[4*N-(2*i+1)]= fftInput[2*i+1]=arrayIn[i];
//        }
//        FastFourierTransformer fastFourierTransformer=new FastFourierTransformer(DftNormalization.STANDARD);
//        Complex[] fftOutput =fastFourierTransformer.transform(fftInput , TransformType.FORWARD);
//
//
//        return Arrays.stream(fftOutput).limit(N)
//                .mapToInt(complex->
//                        (int)Math.round(complex.getReal())
//        ).toArray();
//    }
}
