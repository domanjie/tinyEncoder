package domanjie.dev.jpeg;

public enum HuffmanTableClass {
    DcTableClass (0), AcTableClass(1);
    private final int val;
    HuffmanTableClass(int val) {
        this.val = val;
    }
    public int val() {
        return val;
    }
}
