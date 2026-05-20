package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;

public class WhileStatement extends Statement {

    private final Expression condition;

    private final Statement body;

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