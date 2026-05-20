package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;

public class PrintStatement extends Statement {

    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "print " + expression;
    }
}