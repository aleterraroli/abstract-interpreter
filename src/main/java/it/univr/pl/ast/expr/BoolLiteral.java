package it.univr.pl.ast.expr;

import it.univr.pl.visitor.ASTVisitor;

public class BoolLiteral extends Expression {

    private final boolean value;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public BoolLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}