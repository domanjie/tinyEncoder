package domanjie.dev.jpeg;

public record QuantTable(TableDestinationIdentifier tableDestinationIdentifier,
                         QuantTablePrecision precision,
                         int [][] table ) {
    int length(){
        return 2 + 65 + 64* precision.val();
    };
}
