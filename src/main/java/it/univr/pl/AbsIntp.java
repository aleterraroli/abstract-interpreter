package it.univr.pl;

import it.univr.pl.value.*;

public class AbsIntp extends AbsBaseVisitor<Value> {
    private final Mem mem;

    public AbsIntp(Mem mem) {
        this.mem = mem;
    }

    public Mem getMem() {
        return mem;
    }

    public ComValue visitCom(AbsParser.ComContext ctx) {return (ComValue) visit(ctx);}

    public ExpValue<?> visitExp(AbsParser.ExpContext ctx) {return (ExpValue<?>) visit(ctx);}

    public IntValue visitInt(AbsParser.ExpContext ctx) {return (IntValue) visit(ctx);}

    public BoolValue visitBool(AbsParser.ExpContext ctx) {return (BoolValue) visit(ctx);}

    @Override
	public Value visitMain(AbsParser.MainContext ctx) {
        return visitChildren(ctx);
    }

}
