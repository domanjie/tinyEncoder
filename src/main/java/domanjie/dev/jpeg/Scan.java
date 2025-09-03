package domanjie.dev.jpeg;

import java.util.List;
import java.util.Vector;

public record Scan(
        ImageComponent[] components,
        int startOfSpectralSelect,
        int endOfSpectralSelect,
        int successiveApproximationBitPositionHigh,
        int successiveApproximationBitPositionLow,
        int restartInterval,
        Vector<List<Byte>> entropyEncodedSegments
) {
}
