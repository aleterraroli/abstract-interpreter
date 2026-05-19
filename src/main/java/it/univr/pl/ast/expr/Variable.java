package it.univr.pl.ast.expr;

public class Variable extends Expression {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
