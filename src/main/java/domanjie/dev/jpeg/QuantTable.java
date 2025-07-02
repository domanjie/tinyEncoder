package domanjie.dev.jpeg;

import domanjie.dev.encoder._8x8Block;

public record QuantTable(TableDestinationIdentifier tableDestinationIdentifier,
                         QuantTablePrecision precision,
                         _8x8Block table ) {
    int length(){
        return 2 + 65 + 64* precision.val();
    };
}
