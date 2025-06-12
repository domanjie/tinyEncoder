package domanjie.dev.jpeg;

public enum TableDestinationIdentifier {
    LUMA_TABLE_IDENTIFIER(0), CHROMA_TABLE_IDENTIFIER (1);
    private final int val;

    TableDestinationIdentifier(int val) {
        this.val = val;
    }
    public int val() {
        return val;
    }
}
