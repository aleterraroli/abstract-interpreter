package it.univr.pl.value;

import it.univr.pl.type.StringType;

public class StringValue extends ExpValue<StringType> {

    // Costanti predefinite per evitare istanziazioni continue
    public static final StringValue BOTTOM = new StringValue(StringType.BOTTOM);
    public static final StringValue EMPTY = new StringValue(StringType.EMPTY);
    public static final StringValue ALPHA = new StringValue(StringType.ALPHA);
    public static final StringValue NUMERIC = new StringValue(StringType.NUMERIC);
    public static final StringValue ALPHANUMERIC = new StringValue(StringType.ALPHANUMERIC);

    public StringValue(StringType value) {
        super(value);
    }

    // Metodo LUB che delega alla logica del reticolo
    public StringValue lub(StringValue other) {
        return new StringValue(this.toJavaValue().lub(other.toJavaValue()));
    }

    // Concatenazione che delega alla logica del reticolo
    public StringValue concat(StringValue other) {
        return new StringValue(this.toJavaValue().concat(other.toJavaValue()));
    }
}