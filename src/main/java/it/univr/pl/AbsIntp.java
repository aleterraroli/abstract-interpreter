package it.univr.pl;

import it.univr.pl.value.ComValue;
import it.univr.pl.value.ExpValue;
import it.univr.pl.value.Value;

public class AbsIntp extends AbsBaseVisitor<Value> {
    private final Mem mem;

    public AbsIntp(Mem mem) {
        this.mem = mem;
    }

    public Mem getMem() {
        return mem;
    }

    public ComValue visitCom(AbsParser.ComContext ctx) {return (ComValue) visit(ctx);}

    public ExpValue<?>  visitExp(AbsParser.ExpContext ctx) {return (ExpValue<?>) visit(ctx);}

    @Override
	public Value visitMain(AbsParser.MainContext ctx) {
        return visitChildren(ctx);
    }

}
