package it.univr.pl.ast.stmt;

import java.util.List;

public class BlockStatement extends Statement {

    private final List<Statement> statements;

    public BlockStatement(
            List<Statement> statements) {

        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {

        StringBuilder sb =
                new StringBuilder();

        sb.append("{\n");

        for (Statement stmt : statements) {
            sb.append(stmt)
                    .append("\n");
        }

        sb.append("}");

        return sb.toString();
    }
}