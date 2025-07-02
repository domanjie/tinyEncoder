package domanjie.dev.encoder;

public class Quantizer {
    public  static   _8x8Block quantize(_8x8Block quantTable,  _8x8Block  inputArr ) {

        var result =new int[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                result[x][y] = Math.round((float) inputArr.val()[x][y] / quantTable.val() [x][y]);
            }
        }
        return new _8x8Block(result) ;

    }
}
