package it.univr.pl.value;

public abstract class Value {
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
