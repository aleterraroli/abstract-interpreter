package it.univr.pl.ast.stmt;

import it.univr.pl.visitor.ASTVisitor;

public abstract class Statement {
    public abstract <T> T accept(ASTVisitor<T> visitor);
}
