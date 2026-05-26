package it.univr.pl.ast.expr;

import it.univr.pl.visitor.ASTVisitor;

public class IntLiteral extends Expression {

    private final int value;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public IntLiteral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}