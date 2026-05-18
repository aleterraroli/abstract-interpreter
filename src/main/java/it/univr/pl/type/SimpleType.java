package it.univr.pl.type;

public enum SimpleType implements ExpType {
    INT("int"),
    BOOL("bool");
    private final String name;
    SimpleType(String name) {this.name = name;}
}
