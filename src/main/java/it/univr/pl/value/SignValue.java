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
}