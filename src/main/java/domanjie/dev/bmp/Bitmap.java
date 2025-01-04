package domanjie.dev.bmp;

import domanjie.dev.DIBHeader;
import java.util.Arrays;
import java.util.Objects;

public class Bitmap {


    private  final DIBHeader dibHeader;
    private final byte[][][] pixelArray;

    public byte[][][] getPixelArray() {
        return pixelArray;
    }

    public DIBHeader getDibHeader() {
        return dibHeader;
    }

    public Bitmap(DIBHeader dibHeader, byte[][][] pixelArray){
        this.dibHeader = dibHeader;
        this.pixelArray = pixelArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitmap bitmap = (Bitmap) o;
        return Objects.equals(dibHeader, bitmap.dibHeader) && Objects.deepEquals(pixelArray, bitmap.pixelArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dibHeader, Arrays.deepHashCode(pixelArray));
    }

    @Override
    public String toString() {
        return "Bitmap{" +
                "dibHeader=" + dibHeader +
                ", pixelArray=" + Arrays.deepToString(pixelArray) +
                '}';
    }

}
