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
    public static final SignValue TOP = new SignValue(SignType.TOP);

    public SignValue lub(SignValue other) {
        return new SignValue(this.toJavaValue().lub(other.toJavaValue()));
    }

    public SignValue add(SignValue other) {
        SignType t1 = this.toJavaValue();
        SignType t2 = other.toJavaValue();

        if (t1 == SignType.BOTTOM || t2 == SignType.BOTTOM) return BOTTOM;
        if (t1 == SignType.ZERO) return other;
        if (t2 == SignType.ZERO) return this;
        if (t1 == SignType.POS && t2 == SignType.POS) return POS;
        if (t1 == SignType.NEG && t2 == SignType.NEG) return NEG;
        return TOP;
    }

    public SignValue sub(SignValue other) {
        SignType t2 = other.toJavaValue();
        SignValue invertedRight = BOTTOM;
        if (t2 == SignType.POS) invertedRight = NEG;
        else if (t2 == SignType.NEG) invertedRight = POS;
        else if (t2 == SignType.ZERO) invertedRight = ZERO;
        else if (t2 == SignType.TOP) invertedRight = TOP;
        return this.add(invertedRight);
    }

    public SignValue mul(SignValue other) {
        SignType t1 = this.toJavaValue();
        SignType t2 = other.toJavaValue();
        if (t1 == SignType.BOTTOM || t2 == SignType.BOTTOM) return BOTTOM;
        if (t1 == SignType.ZERO || t2 == SignType.ZERO) return ZERO;
        if (t1 == SignType.TOP || t2 == SignType.TOP) return TOP;
        if (t1 == t2) return POS;
        return NEG;
    }

    public SignValue div(SignValue other) {
        SignType t1 = this.toJavaValue();
        SignType t2 = other.toJavaValue();
        if (t1 == SignType.BOTTOM || t2 == SignType.BOTTOM) return BOTTOM;
        if (t2 == SignType.ZERO) return BOTTOM;
        if (t1 == SignType.ZERO) return ZERO;
        if (t2 == SignType.TOP || t1 == SignType.TOP) return TOP;
        if (t1 == t2) return POS;
        return NEG;
    }
}
}