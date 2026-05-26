package it.univr.pl.ast;

import it.univr.pl.ast.stmt.Statement;
import it.univr.pl.visitor.ASTVisitor;

import java.util.List;

public class Program {

    private final List<Statement> statements;

    public Program(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Statement stmt : statements) {
            sb.append(stmt).append("\n");
        }
        return sb.toString();
    }
}