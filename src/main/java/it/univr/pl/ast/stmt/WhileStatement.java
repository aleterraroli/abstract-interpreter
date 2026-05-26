package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;
import it.univr.pl.visitor.ASTVisitor;

public class WhileStatement extends Statement {

    private final Expression condition;

    private final Statement body;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public WhileStatement(
            Expression condition,
            Statement body) {

        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getBody() {
        return body;
    }

    @Override
    public String toString() {

        return "while (" +
                condition +
                ") " +
                body;
    }
}