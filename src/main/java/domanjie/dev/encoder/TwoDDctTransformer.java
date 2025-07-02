package domanjie.dev.encoder;

import org.apache.commons.math3.util.FastMath;

public class TwoDDctTransformer {
    private static final double[][] precomputedCosines;
    private static final int DataUnitL=8;
    static {
        precomputedCosines= new double[DataUnitL][DataUnitL];
        for(int freq=0; freq <DataUnitL; freq++ ){
            for(int x=0; x<DataUnitL; x++)
                precomputedCosines[freq][x]= Math.cos(((2*x+1)*freq*Math.PI)/(2*DataUnitL));
        }
    }

    //performs a 2d  dct(type 2) on an array whose dimension is greater than  2
    public static  _8x8Block twoDimensionalForwardDct(_8x8Block input) {
        int[][] dct = new int[DataUnitL][DataUnitL];
        double cu, cv;
        for (int u = 0; u < DataUnitL; u++) {
            for (int v = 0; v < DataUnitL; v++) {
                if (u == 0)
                    cu = 1 / FastMath.sqrt(DataUnitL);
                else
                    cu = FastMath.sqrt(2.0/DataUnitL);
                if (v == 0)
                    cv = 1 / Math.sqrt(DataUnitL);
                else cv = FastMath.sqrt(2.0 /DataUnitL);
                double sum = 0;
                for (int x = 0; x < DataUnitL; x++) {
                    for (int y = 0; y <DataUnitL; y++)
                        sum += input.val()[x][y] * precomputedCosines[u][x] * precomputedCosines[v][y];
                }
                dct[u][v] = (int) Math.round(cu * cv * sum);
            }
        }
        return new _8x8Block(dct);

    }
}
