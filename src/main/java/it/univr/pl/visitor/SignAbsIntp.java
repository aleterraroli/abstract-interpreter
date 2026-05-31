package it.univr.pl.visitor;

import it.univr.pl.AbsBaseVisitor;
import it.univr.pl.AbsParser;
import it.univr.pl.SignAbsMem;
import it.univr.pl.type.SignType;
import it.univr.pl.value.BoolValue;
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

    @Override
    public Value visitId(AbsParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return mem.get(id);
    }

    @Override
    public Value visitIntVal(AbsParser.IntValContext ctx) {
        int literal = Integer.parseInt(ctx.INT().getText());
        if (literal > 0) return SignValue.POS;
        if (literal < 0) return SignValue.NEG;
        return SignValue.ZERO;
    }

    @Override
    public Value visitBoolVal(AbsParser.BoolValContext ctx) {
        return new BoolValue(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    @Override
    public Value visitAddSub(AbsParser.AddSubContext ctx) {
        SignValue left = (SignValue) visit(ctx.exp(0));
        SignValue right = (SignValue) visit(ctx.exp(1));

        if (ctx.op.getType() == AbsParser.ADD) {
            return left.add(right);
        } else {
            return left.sub(right);
        }
    }

    @Override
    public Value visitMulDiv(AbsParser.MulDivContext ctx) {
        SignValue left = (SignValue) visit(ctx.exp(0));
        SignValue right = (SignValue) visit(ctx.exp(1));

        if (ctx.op.getType() == AbsParser.MUL) {
            return left.mul(right);
        } else {
            return left.div(right);
        }
    }

    @Override
    public Value visitIfElse(AbsParser.IfElseContext ctx) {
        // OSTACOLO 1: Spesso non sappiamo staticamente se la condizione è True o False.
        // Di conseguenza, dobbiamo analizzare ENTRAMBI i rami in parallelo e poi fare il LUB delle memorie.

        SignAbsMem branchThenMem = new SignAbsMem(this.mem);
        SignAbsIntp intpThen = new SignAbsIntp(branchThenMem);
        intpThen.visit(ctx.com(0));

        SignAbsMem branchElseMem = new SignAbsMem(this.mem);
        SignAbsIntp intpElse = new SignAbsIntp(branchElseMem);
        intpElse.visit(ctx.com(1));

        // Flessione del flusso: lo stato finale è il Least Upper Bound delle memorie dei due rami
        this.mem.lub(branchThenMem);
        this.mem.lub(branchElseMem);

        return ComValue.INSTANCE;
    }

    @Override
    public Value visitIf(AbsParser.IfContext ctx) {
        // Se c'è solo l'If senza Else, l'alternativa implicita è non fare nulla (mantenere la memoria intatta)
        SignAbsMem branchThenMem = new SignAbsMem(this.mem);
        SignAbsIntp intpThen = new SignAbsIntp(branchThenMem);
        intpThen.visit(ctx.com());

        // LUB tra la memoria modificata dal Then e la memoria originale (come se l'If fosse stato saltato)
        this.mem.lub(branchThenMem);
        return ComValue.INSTANCE;
    }

}
