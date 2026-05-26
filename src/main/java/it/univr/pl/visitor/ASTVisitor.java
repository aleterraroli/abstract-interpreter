package it.univr.pl.visitor;
import it.univr.pl.ast.Program;
import it.univr.pl.ast.expr.*;
import it.univr.pl.ast.stmt.*;
public interface ASTVisitor<T> {
    T visit(Program program);
    T visit(BinaryExpression expr);
    T visit(UnaryExpression expr);
    T visit(Variable expr);
    T visit(IntLiteral expr);
    T visit(BoolLiteral expr);
    T visit(DeclarationStatement stmt);
    T visit(AssignStatement stmt);
    T visit(IfStatement stmt);
    T visit(WhileStatement stmt);
    T visit(PrintStatement stmt);
    T visit(BlockStatement stmt);
}
