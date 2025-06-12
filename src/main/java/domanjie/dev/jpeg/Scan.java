package domanjie.dev.jpeg;

import java.util.List;

public record Scan(
        ImageComponent[] components,
        int startOfSpectralSelect,
        int endOfSpectralSelect,
        int successiveApproximationBitPositionHigh,
        int successiveApproximationBitPositionLow,
        List<Byte> EntropyEncodedSegment
) {
}
