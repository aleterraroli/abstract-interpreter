package it.univr.pl.type;

public enum StringType {
    BOTTOM,
    EMPTY,
    ALPHA,
    NUMERIC,
    ALPHANUMERIC;

    /**
     * Operatore LUB (Least Upper Bound).
     * Riconcilia i valori provenienti da rami divergenti (es. If-Else).
     */
    public StringType lub(StringType other) {
        if (this == other) return this;

        if (this == BOTTOM) return other;
        if (other == BOTTOM) return this;

        return ALPHANUMERIC;
    }

    /**
     * Operatore per la concatenazione astratta tra due proprietà.
     */
    public StringType concat(StringType other) {
        if (this == BOTTOM || other == BOTTOM) return BOTTOM;

        if (this == EMPTY) return other;
        if (other == EMPTY) return this;

        if (this == ALPHA && other == ALPHA) return ALPHA;
        if (this == NUMERIC && other == NUMERIC) return NUMERIC;

        return ALPHANUMERIC;
    }
}