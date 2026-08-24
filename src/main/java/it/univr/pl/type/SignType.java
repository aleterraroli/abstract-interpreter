package it.univr.pl.type;

public enum SignType implements ExpType {
    BOTTOM("⊥"),
    NEG("-"),
    ZERO("0"),
    POS("+"),
    ZERO_MINUS("0-"),
    ZERO_PLUS("0+"),
    NOT_ZERO("!=0"),
    TOP("T");

    private final String name;

    SignType(String name) { this.name = name; }

    @Override
    public String getName() { return name; }

    @Override
    public boolean isCompatible(Type other) {
        if (other == SimpleType.INT) return true;
        return this == other || other == SignType.TOP;
    }

    public SignType lub(SignType other) {
        if (this == other) return this;
        if (this == BOTTOM) return other;
        if (other == BOTTOM) return this;

        if (this == TOP || other == TOP) return TOP;

        if ((this == ZERO && other == POS) || (this == POS && other == ZERO)) return ZERO_PLUS;
        if ((this == ZERO && other == NEG) || (this == NEG && other == ZERO)) return ZERO_MINUS;
        if ((this == POS && other == NEG) || (this == NEG && other == POS)) return NOT_ZERO;

        if ((this == ZERO_PLUS && other == ZERO_MINUS) || (this == ZERO_MINUS && other == ZERO_PLUS)) return TOP;

        return TOP;
    }
}