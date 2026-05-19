package it.univr.pl;

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
}
