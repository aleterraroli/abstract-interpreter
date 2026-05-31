package it.univr.pl.visitor;

import it.univr.pl.AbsBaseVisitor;
import it.univr.pl.AbsParser;
import it.univr.pl.Mem;
import it.univr.pl.type.ExpType;
import it.univr.pl.type.TypeUtils;
import it.univr.pl.value.*;

public class AbsIntp extends AbsBaseVisitor<Value> {
    private final Mem mem;

    public AbsIntp(Mem mem) {
        this.mem = mem;
    }

    public Mem getMem() {
        return mem;
    }

    private ComValue visitCom(AbsParser.ComContext ctx) {return (ComValue) visit(ctx);}

    private ExpValue<?> visitExp(AbsParser.ExpContext ctx) {return (ExpValue<?>) visit(ctx);}

    private IntValue visitIntExp(AbsParser.ExpContext ctx) {return (IntValue) visit(ctx);}

    private BoolValue visitBoolExp(AbsParser.ExpContext ctx) {return (BoolValue) visit(ctx);}

    private double unwrapToDouble(IntValue value) {
        return value.toJavaValue().doubleValue();
    }

    @Override
	public Value visitMain(AbsParser.MainContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ComValue visitDecl(AbsParser.DeclContext ctx) {

        ExpType type = TypeUtils.fromString(ctx.TYPE().getText());
        String id = ctx.ID().getText();
        mem.add(id, (ExpValue<?>) type);
        if (ctx.exp() != null) {
            ExpValue<?> val = visitExp(ctx.exp());
            mem.updateValue(id, val);
        }

        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitIf(AbsParser.IfContext ctx) {
        if (!visitBoolExp(ctx.exp()).toJavaValue())
            return ComValue.INSTANCE;

        AbsIntp interpreter = new AbsIntp(mem);
        interpreter.visitCom(ctx.com());
        mem.updateValues(interpreter.getMem());

        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitIfElse(AbsParser.IfElseContext ctx) {
        AbsIntp interpreter = new AbsIntp(mem);
        if (visitBoolExp(ctx.exp()).toJavaValue())
            interpreter.visitCom(ctx.com(0));
        else
            interpreter.visitCom(ctx.com(1));
        mem.updateValues(interpreter.getMem());

        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitAssign(AbsParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        ExpValue<?> v = visitExp(ctx.exp());

        mem.updateValue(id, v); // safe due to type checking

        return ComValue.INSTANCE;
    }

    @Override
    public ComValue visitWhile(AbsParser.WhileContext ctx) {
        if (!visitBoolExp(ctx.exp()).toJavaValue())
            return ComValue.INSTANCE;

        AbsIntp interpreter = new AbsIntp(mem);
        interpreter.visitCom(ctx.com());
        mem.updateValues(interpreter.getMem());

        return visitWhile(ctx);
    }

    @Override
    public ComValue visitPrint(AbsParser.PrintContext ctx) {
        System.out.println(visitIntExp(ctx.exp()).toJavaValue());
        return ComValue.INSTANCE;
    }

    @Override
    public IntValue visitIntVal(AbsParser.IntValContext ctx) {
        return new IntValue(Integer.parseInt(ctx.INT().getText()));
    }

    @Override
    public BoolValue visitBoolVal(AbsParser.BoolValContext ctx) {
        return new BoolValue(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    @Override
    public Value visitParExp(AbsParser.ParExpContext ctx) {
        return visitExp(ctx.exp());
    }

    @Override
    public Value visitNot(AbsParser.NotContext ctx) {
        return new BoolValue(!visitBoolExp(ctx.exp()).toJavaValue());
    }

    @Override
    public ExpValue<?> visitMulDiv(AbsParser.MulDivContext ctx) {
        IntValue left = visitIntExp(ctx.exp(0));
        IntValue right = visitIntExp(ctx.exp(1));

        Double result;
        switch (ctx.op.getType()) {
            case AbsParser.DIV -> result = unwrapToDouble(left) / unwrapToDouble(right);
            case AbsParser.MUL -> result = unwrapToDouble(left) * unwrapToDouble(right);
            default -> result = null; // unreachable code
        }

        assert result != null; // always true
        return new IntValue((int) result.doubleValue());
    }

    @Override
    public ExpValue<?> visitAddSub(AbsParser.AddSubContext ctx) {
        IntValue left = visitIntExp(ctx.exp(0));
        IntValue right = visitIntExp(ctx.exp(1));

        Double result;
        switch (ctx.op.getType()) {
            case AbsParser.ADD -> result = unwrapToDouble(left) + unwrapToDouble(right);
            case AbsParser.SUB -> result = unwrapToDouble(left) - unwrapToDouble(right);
            default -> result = null; // unreachable code
        }

        assert result != null; // always true
        return new IntValue((int) result.doubleValue());
    }

    @Override
    public BoolValue visitEqExp(AbsParser.EqExpContext ctx) {
        ExpValue<?> left = visitExp(ctx.exp(0));
        ExpValue<?> right = visitExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case AbsParser.EQQ -> new BoolValue(left.equals(right));
            case AbsParser.NEQ -> new BoolValue(!left.equals(right));
            default -> null; // unreachable code
        };
    }

    @Override
    public ExpValue<?> visitId(AbsParser.IdContext ctx) {
        String id = ctx.ID().getText();

        return mem.getValue(id);
    }

    @Override
    public BoolValue visitCmpExp(AbsParser.CmpExpContext ctx) {
        IntValue left = visitIntExp(ctx.exp(0));
        IntValue right = visitIntExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case AbsParser.GEQ -> new BoolValue(unwrapToDouble(left) >= unwrapToDouble(right));
            case AbsParser.LEQ -> new BoolValue(unwrapToDouble(left) <= unwrapToDouble(right));
            case AbsParser.LT -> new BoolValue(unwrapToDouble(left) < unwrapToDouble(right));
            case AbsParser.GT -> new BoolValue(unwrapToDouble(left) > unwrapToDouble(right));
            default -> null; // unreachable code
        };
    }

    @Override
    public BoolValue visitLogic(AbsParser.LogicContext ctx) {
        BoolValue left = visitBoolExp(ctx.exp(0));
        BoolValue right = visitBoolExp(ctx.exp(1));

        return switch (ctx.op.getType()) {
            case AbsParser.AND -> new BoolValue(left.toJavaValue() && right.toJavaValue());
            case AbsParser.OR -> new BoolValue(left.toJavaValue() || right.toJavaValue());
            default -> null; // unreachable code
        };
    }
}
