package it.univr.pl.visitor;

import it.univr.pl.AbsBaseVisitor;
import it.univr.pl.AbsParser;
import it.univr.pl.SignAbsMem;
import it.univr.pl.value.ComValue;
import it.univr.pl.value.SignValue;
import it.univr.pl.value.Value;

public class SignAbsIntp extends AbsBaseVisitor<Value> {
    private final SignAbsMem mem;

    public SignAbsIntp(SignAbsMem mem) {
        this.mem = mem;
    }

    public SignAbsMem getMem() {
        return mem;
    }

    @Override
    public Value visitMain(AbsParser.MainContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Value visitDecl(AbsParser.DeclContext ctx) {
        String id = ctx.ID().getText();
        SignValue initialSign = SignValue.BOTTOM;
        if(ctx.exp() != null)
            initialSign = (SignValue) visit(ctx.exp());
        mem.add(id, initialSign);
        return ComValue.INSTANCE;
    }

    @Override
    public Value visitAssign(AbsParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        SignValue sign = (SignValue) visit(ctx.exp());
        mem.update(id, sign);
        return ComValue.INSTANCE;
    }


}
