package it.univr.pl.value;

import it.univr.pl.type.SignType;

public class SignValue extends ExpValue<SignType> {

    public SignValue(SignType value) {
        super(value);
    }

    public static final SignValue BOTTOM = new SignValue(SignType.BOTTOM);
    public static final SignValue ZERO = new SignValue(SignType.ZERO);
    public static final SignValue POS = new SignValue(SignType.POS);
    public static final SignValue NEG = new SignValue(SignType.NEG);
    public static final SignValue ZERO_PLUS = new SignValue(SignType.ZERO_PLUS);
    public static final SignValue ZERO_MINUS = new SignValue(SignType.ZERO_MINUS);
    public static final SignValue NOT_ZERO = new SignValue(SignType.NOT_ZERO);
    public static final SignValue TOP = new SignValue(SignType.TOP);

    public SignValue lub(SignValue other) {
        return new SignValue(this.toJavaValue().lub(other.toJavaValue()));
    }

    public SignValue add(SignValue other) {
        SignType t1 = this.toJavaValue();
        SignType t2 = other.toJavaValue();

        if (t1 == SignType.BOTTOM || t2 == SignType.BOTTOM) return BOTTOM;
        if (t1 == SignType.TOP || t2 == SignType.TOP) return TOP;
        if (t1 == SignType.ZERO) return other;
        if (t2 == SignType.ZERO) return this;

        if (t1 == t2) {
            if (t1 == SignType.POS) return POS;
            if (t1 == SignType.NEG) return NEG;
            if (t1 == SignType.ZERO_PLUS) return ZERO_PLUS;
            if (t1 == SignType.ZERO_MINUS) return ZERO_MINUS;
            if (t1 == SignType.NOT_ZERO) return TOP;
        }

        if ((t1 == SignType.POS && t2 == SignType.ZERO_PLUS) || (t2 == SignType.POS && t1 == SignType.ZERO_PLUS)) return POS;
        if ((t1 == SignType.NEG && t2 == SignType.ZERO_MINUS) || (t2 == SignType.NEG && t1 == SignType.ZERO_MINUS)) return NEG;

        return TOP;
    }

    private SignValue invert() {
        return switch (this.toJavaValue()) {
            case POS -> NEG;
            case NEG -> POS;
            case ZERO_PLUS -> ZERO_MINUS;
            case ZERO_MINUS -> ZERO_PLUS;
            case ZERO, NOT_ZERO, TOP, BOTTOM -> this;
        };
    }

    public SignValue sub(SignValue other) {
        return this.add(other.invert());
    }

    public SignValue mul(SignValue other) {
        SignType t1 = this.toJavaValue();
        SignType t2 = other.toJavaValue();

        if (t1 == SignType.BOTTOM || t2 == SignType.BOTTOM) return BOTTOM;
        if (t1 == SignType.ZERO || t2 == SignType.ZERO) return ZERO;
        if (t1 == SignType.TOP || t2 == SignType.TOP) return TOP;

        if (t1 == SignType.POS && t2 == SignType.POS) return POS;
        if (t1 == SignType.NEG && t2 == SignType.NEG) return POS;
        if ((t1 == SignType.POS && t2 == SignType.NEG) || (t1 == SignType.NEG && t2 == SignType.POS)) return NEG;
        if (t1 == SignType.NOT_ZERO && t2 == SignType.NOT_ZERO) return NOT_ZERO;

        if ((t1 == SignType.ZERO_PLUS && t2 == SignType.POS) || (t2 == SignType.ZERO_PLUS && t1 == SignType.POS) || (t1 == SignType.ZERO_PLUS && t2 == SignType.ZERO_PLUS)) return ZERO_PLUS;
        if ((t1 == SignType.ZERO_MINUS && t2 == SignType.NEG) || (t2 == SignType.ZERO_MINUS && t1 == SignType.NEG) || (t1 == SignType.ZERO_MINUS && t2 == SignType.ZERO_MINUS)) return ZERO_PLUS;

        if ((t1 == SignType.ZERO_PLUS && t2 == SignType.NEG) || (t2 == SignType.ZERO_PLUS && t1 == SignType.NEG) || (t1 == SignType.ZERO_PLUS && t2 == SignType.ZERO_MINUS)) return ZERO_MINUS;
        if ((t1 == SignType.ZERO_MINUS && t2 == SignType.POS) || (t2 == SignType.ZERO_MINUS && t1 == SignType.POS)) return ZERO_MINUS;

        if (t1 == SignType.NOT_ZERO || t2 == SignType.NOT_ZERO) return TOP;

        return TOP;
    }

    public SignValue div(SignValue other) {
        SignType t1 = this.toJavaValue();
        SignType t2 = other.toJavaValue();

        if (t1 == SignType.BOTTOM || t2 == SignType.BOTTOM) return BOTTOM;

        if (t2 == SignType.ZERO) return BOTTOM;

        if (t1 == SignType.ZERO) return ZERO;

        if (t2 == SignType.TOP || t2 == SignType.ZERO_PLUS || t2 == SignType.ZERO_MINUS) return TOP;

        if (t1 == SignType.POS && t2 == SignType.POS) return ZERO_PLUS;
        if (t1 == SignType.NEG && t2 == SignType.NEG) return ZERO_PLUS;
        if ((t1 == SignType.POS && t2 == SignType.NEG) || (t1 == SignType.NEG && t2 == SignType.POS)) return ZERO_MINUS;

        return TOP;
    }
}