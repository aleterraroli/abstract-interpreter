package it.univr.pl.ast.stmt;

import it.univr.pl.ast.expr.Expression;
import it.univr.pl.type.Type;
import it.univr.pl.visitor.ASTVisitor;

public class DeclarationStatement extends Statement {

    private final Type type;

    private final String variable;

    private final Expression initializer;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public DeclarationStatement(
            Type type,
            String variable,
            Expression initializer) {

        this.type = type;
        this.variable = variable;
        this.initializer = initializer;
    }

    public Type getType() {
        return type;
    }

    public String getVariable() {
        return variable;
    }

    public Expression getInitializer() {
        return initializer;
    }

    @Override
    public String toString() {

        if (initializer == null)
            return type + " " + variable;

        return type + " " +
                variable +
                " = " +
                initializer;
    }
}