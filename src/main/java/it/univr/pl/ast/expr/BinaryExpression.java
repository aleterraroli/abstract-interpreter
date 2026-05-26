package it.univr.pl.ast.expr;
import it.univr.pl.ast.operator.BinaryOperator;
import it.univr.pl.visitor.ASTVisitor;

public class BinaryExpression extends Expression {

    private final Expression left;

    private final Expression right;

    private final BinaryOperator operator;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public BinaryExpression(
            Expression left,
            BinaryOperator operator,
            Expression right) {

        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    @Override
    public String toString() {

        return "(" +
                left +
                " " +
                operator +
                " " +
                right +
                ")";
    }
}