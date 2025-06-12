package domanjie.dev.jpeg;

public enum QuantTablePrecision {
    _8BIT(0), _16BIT (1);
    private final int val;

    QuantTablePrecision(int val) {
        this.val = val;
    }
    public int val() {
        return val;
    }
}
