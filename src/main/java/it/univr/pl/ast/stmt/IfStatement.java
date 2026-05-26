package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;
import it.univr.pl.visitor.ASTVisitor;

public class IfStatement extends Statement {

    private final Expression condition;

    private final Statement thenBranch;

    private final Statement elseBranch;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public IfStatement(
            Expression condition,
            Statement thenBranch,
            Statement elseBranch) {

        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThenBranch() {
        return thenBranch;
    }

    public Statement getElseBranch() {
        return elseBranch;
    }

    @Override
    public String toString() {

        if (elseBranch == null) {

            return "if (" +
                    condition +
                    ") " +
                    thenBranch;
        }

        return "if (" +
                condition +
                ") " +
                thenBranch +
                " else " +
                elseBranch;
    }
}