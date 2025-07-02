package domanjie.dev.encoder;

import java.util.Arrays;
import java.util.Objects;

public record _8x8Block(int [][] val) {
    public _8x8Block {
        if(isNon8x8(val))
            throw new IllegalArgumentException("input must be an 8x8 array");
    }
    private static boolean isNon8x8( int  [][] inputArr ){
        return (inputArr.length != 8 || inputArr[0].length != 8);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        _8x8Block x8Block = (_8x8Block) o;
        return Objects.deepEquals(val, x8Block.val);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(val);
    }
}
