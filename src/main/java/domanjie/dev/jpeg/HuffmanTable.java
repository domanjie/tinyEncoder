package domanjie.dev.jpeg;

public record HuffmanTable(HuffmanTableClass tableClass,
                           TableDestinationIdentifier tableDestinationIdentifier,
                           int [] bitList,
                           int[] huffVal) {
}
