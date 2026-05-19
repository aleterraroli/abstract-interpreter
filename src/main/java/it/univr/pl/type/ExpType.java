package it.univr.pl.type;

public interface ExpType extends Type{
    String getName();

    boolean isCompatible(Type other);
}
