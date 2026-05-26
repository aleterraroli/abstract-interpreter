package it.univr.pl.ast.stmt;
import it.univr.pl.ast.expr.Expression;
import it.univr.pl.visitor.ASTVisitor;

public class AssignStatement extends Statement {

    private final String variable;

    private final Expression expression;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public AssignStatement(
            String variable,
            Expression expression) {

        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {

        return variable +
                " = " +
                expression;
    }
}