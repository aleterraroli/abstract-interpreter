package it.univr.pl.visitor;

import it.univr.pl.AbsBaseVisitor;
import it.univr.pl.AbsParser;
import it.univr.pl.ast.*;
import it.univr.pl.ast.expr.*;
import it.univr.pl.ast.operator.*;
import it.univr.pl.ast.stmt.*;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilderVisitor extends AbsBaseVisitor<Object> {

    @Override
    public Object visitMain(AbsParser.MainContext ctx) {
        List<Statement> statements = new ArrayList<>();
        for(AbsParser.ComContext com : ctx.com()) {
            statements.add((Statement) visit(com));
        }
        return new Program(statements);
    }

    @Override
    public Object visitIntVal(AbsParser.IntValContext ctx) {
        return new IntLiteral(Integer.parseInt(ctx.INT().getText()));
    }

    @Override
    public Object visitBoolVal(AbsParser.BoolValContext ctx) {
        return new BoolLiteral(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    @Override
    public Object visitId(AbsParser.IdContext ctx) {
        return new Variable(ctx.ID().getText());
    }

    @Override
    public Object visitAddSub(AbsParser.AddSubContext ctx) {
        Expression left = (Expression) visit(ctx.exp(0));
        Expression right = (Expression) visit(ctx.exp(1));
        BinaryOperator op = ctx.op.getType() == AbsParser.ADD ? BinaryOperator.ADD : BinaryOperator.SUB;
        return new BinaryExpression(left, op, right);
    }

    @Override
    public Object visitMulDiv(AbsParser.MulDivContext ctx) {
        Expression left = (Expression) visit(ctx.exp(0));
        Expression right = (Expression) visit(ctx.exp(1));
        BinaryOperator op = ctx.op.getType() == AbsParser.MUL ? BinaryOperator.MUL : BinaryOperator.DIV;
        return new BinaryExpression(left, op, right);
    }

    @Override
    public Object visitAssign(AbsParser.AssignContext ctx) {
        return new AssignStatement(ctx.ID().getText(),(Expression) visit(ctx.exp()));
    }

    @Override
    public Object visitIfElse(AbsParser.IfElseContext ctx) {
        return new IfStatement((Expression) visit(ctx.exp()),(Statement) visit(ctx.com(0)),(Statement) visit(ctx.com(1)));
    }

    @Override
    public Object visitNot(AbsParser.NotContext ctx) {
        return new UnaryExpression(UnaryOperator.NOT,(Expression) visit(ctx.exp()));
    }

    @Override
    public Object visitCmpExp(AbsParser.CmpExpContext ctx) {

        Expression left = (Expression) visit(ctx.exp(0));
        Expression right = (Expression) visit(ctx.exp(1));
        BinaryOperator op;

        switch (ctx.op.getType()) {

            case AbsParser.LT:
                op = BinaryOperator.LT;
                break;

            case AbsParser.LEQ:
                op = BinaryOperator.LEQ;
                break;

            case AbsParser.GT:
                op = BinaryOperator.GT;
                break;

            case AbsParser.GEQ:
                op = BinaryOperator.GEQ;
                break;

            default:
                throw new RuntimeException("Invalid comparison operator");
        }

        return new BinaryExpression(left, op, right);
    }

    @Override
    public Object visitEqExp(AbsParser.EqExpContext ctx) {
        Expression left = (Expression) visit(ctx.exp(0));
        Expression right = (Expression) visit(ctx.exp(1));
        BinaryOperator op;
        switch (ctx.op.getType()) {
            case AbsParser.EQQ:
                op = BinaryOperator.EQ;
                break;
            case AbsParser.NEQ:
                op = BinaryOperator.NEQ;
                break;
            default:
                throw new RuntimeException("Invalid equality operator");
        }
        return new BinaryExpression(left, op, right);
    }

    @Override
    public Object visitLogic(AbsParser.LogicContext ctx) {
        Expression left = (Expression) visit(ctx.exp(0));
        Expression right = (Expression) visit(ctx.exp(1));
        BinaryOperator op;
        switch (ctx.op.getType()) {
            case AbsParser.AND:
                op = BinaryOperator.AND;
                break;
            case AbsParser.OR:
                op = BinaryOperator.OR;
                break;
            default:
                throw new RuntimeException("Invalid logic operator");

        }
        return new BinaryExpression(left, op, right);
    }

    @Override
    public Object visitParExp(AbsParser.ParExpContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public Object visitIf(AbsParser.IfContext ctx) {
        return new IfStatement((Expression) visit(ctx.exp()), (Statement) visit(ctx.com()),null);
    }

    @Override
    public Object visitWhile(AbsParser.WhileContext ctx) {
        return new WhileStatement((Expression) visit(ctx.exp()), (Statement) visit(ctx.com()));
    }
}