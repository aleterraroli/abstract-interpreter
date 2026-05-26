package it.univr.pl.ast.expr;
import it.univr.pl.ast.operator.UnaryOperator;
import it.univr.pl.visitor.ASTVisitor;

public class UnaryExpression extends Expression {

    private final UnaryOperator operator;

    private final Expression expression;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public UnaryExpression(
            UnaryOperator operator,
            Expression expression) {

        this.operator = operator;
        this.expression = expression;
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {

        return "(" +
                operator +
                " " +
                expression +
                ")";
    }
}