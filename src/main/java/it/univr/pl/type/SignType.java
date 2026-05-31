package it.univr.pl.type;

public enum SignType implements ExpType {
    BOTTOM("⊥"),
    ZERO("0"),
    POS("+"),
    NEG("-"),
    TOP("⊤");

    private final String name;

    SignType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isCompatible(Type other) {
        if (other == SimpleType.INT) {
            return true;
        }
        return this == other || other == SignType.TOP;
    }

    public SignType lub(SignType other) {
        if (this == BOTTOM) return other;
        if (other == BOTTOM) return this;
        if (this == TOP || other == TOP) return TOP;
        if (this == other) return this;

        return TOP;
    }
}