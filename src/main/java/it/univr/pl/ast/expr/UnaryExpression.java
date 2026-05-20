package it.univr.pl.ast.expr;
import it.univr.pl.ast.operator.UnaryOperator;

public class UnaryExpression extends Expression {

    private final UnaryOperator operator;
    private final Expression expression;

    public UnaryExpression(UnaryOperator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }
}
