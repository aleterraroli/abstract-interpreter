package it.univr.pl.ast.expr;

public class BoolLiteral extends Expression {

    private boolean value;

    public BoolLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue(){
        return value;
    }

}
