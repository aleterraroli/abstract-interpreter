package it.univr.pl.visitor;

import it.univr.pl.ast.*;
import it.univr.pl.ast.expr.*;
import it.univr.pl.ast.stmt.*;
import it.univr.pl.type.*;
import it.univr.pl.exception.*;
import java.util.HashMap;
import java.util.Map;

public class TypeCheckVisitor implements ASTVisitor<Type> {

    private final Map<String, Type> simbolTable = new HashMap<>();

    @Override
    public Type visit(Program program) {
        return null;
    }

    @Override
    public Type visit(BinaryExpression expr) {
        return null;
    }

    @Override
    public Type visit(UnaryExpression expr) {
        return null;
    }

    @Override
    public Type visit(Variable expr) {
        return null;
    }

    @Override
    public Type visit(IntLiteral expr) {
        return null;
    }

    @Override
    public Type visit(BoolLiteral expr) {
        return null;
    }

    @Override
    public Type visit(DeclarationStatement stmt) {
        return null;
    }

    @Override
    public Type visit(AssignStatement stmt) {
        return null;
    }

    @Override
    public Type visit(IfStatement stmt) {
        return null;
    }

    @Override
    public Type visit(WhileStatement stmt) {
        return null;
    }

    @Override
    public Type visit(PrintStatement stmt) {
        return null;
    }

    @Override
    public Type visit(BlockStatement stmt) {
        return null;
    }
}
