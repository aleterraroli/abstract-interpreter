package it.univr.pl.ast.expr;

import it.univr.pl.visitor.ASTVisitor;

public class Variable extends Expression {

    private final String name;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}