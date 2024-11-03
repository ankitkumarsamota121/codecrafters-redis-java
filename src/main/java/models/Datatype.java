package models;

public enum Datatype {
    ARRAYS('*');

    private final char indicator;

    private Datatype(final char indicator) {
        this.indicator = indicator;
    }

    public char getIndicator() {
        return this.indicator;
    }
}
