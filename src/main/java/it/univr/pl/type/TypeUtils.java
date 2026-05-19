package it.univr.pl.type;

import it.univr.pl.value.*;

public class TypeUtils {

    public static ExpType fromString(String str) {
        return switch (str) {
            case "int" -> SimpleType.INT;
            case "bool" -> SimpleType.BOOL;

            default -> null;
        };
    }

    public static ExpType fromValue(ExpValue<?> value) {
        if (value instanceof IntValue)
            return SimpleType.INT;
        if (value instanceof BoolValue)
            return SimpleType.BOOL;

        return null; // unreachable code
    }

}
