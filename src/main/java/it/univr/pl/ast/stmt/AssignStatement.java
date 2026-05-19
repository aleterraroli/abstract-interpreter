package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;

public class AssignStatement extends Statement {

    private final String variable;

    private final Expression expression;

    public AssignStatement(
            String variable,
            Expression expression) {

        this.variable = variable;
        this.expression = expression;
    }
}
