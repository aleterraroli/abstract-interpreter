package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;
import it.univr.pl.visitor.ASTVisitor;

public class PrintStatement extends Statement {

    private final Expression expression;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

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