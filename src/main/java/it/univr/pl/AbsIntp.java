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

    private IntValue visitInt(AbsParser.ExpContext ctx) {return (IntValue) visit(ctx);}

    private BoolValue visitBool(AbsParser.ExpContext ctx) {return (BoolValue) visit(ctx);}

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

}
