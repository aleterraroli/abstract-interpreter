package it.univr.pl.ast.expr;

import it.univr.pl.visitor.ASTVisitor;

public abstract class Expression {
    public abstract <T> T accept(ASTVisitor<T> visitor);
}
