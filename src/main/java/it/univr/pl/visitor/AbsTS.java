package it.univr.pl.visitor;

import it.univr.pl.AbsBaseVisitor;
import it.univr.pl.AbsParser;
import it.univr.pl.exception.TypeMismatchException;
import it.univr.pl.exception.VarDeclarationException;
import it.univr.pl.type.*;
import java.util.HashMap;
import java.util.Map;

public class AbsTS extends AbsBaseVisitor<Type> {

    private final Map<String, ExpType> typeMap;

    public AbsTS(Map<String, ExpType> typeMap) {
        this.typeMap = new HashMap<>(typeMap);
    }

    public AbsTS() {
        typeMap = new HashMap<>();
    }

    public Map<String, ExpType> getTypeMap() {
        return typeMap;
    }

    private ComType visitCom(AbsParser.ComContext ctx) {
        return (ComType) visit(ctx);
    }

    private SimpleType visitBoolExp(AbsParser.ExpContext ctx) {
        ExpType expType = (ExpType) visit(ctx);
        if (!expType.isCompatible(SimpleType.BOOL)) { // not boolean expression
            String err = "Type mismatch: boolean expression expected.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }

        return SimpleType.BOOL;
    }

    private SimpleType visitIntExp(AbsParser.ExpContext ctx) {
        ExpType expType = (ExpType) visit(ctx);
        if (!expType.isCompatible(SimpleType.INT)) { // not numeric expression
            String err = "Type mismatch: numeric expression expected.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }

        return (SimpleType) expType;
    }

    private boolean tryToUpcast(ExpType typeA, ExpType typeB) {
        return typeA == typeB || typeA.isCompatible(typeB) || typeB.isCompatible(typeA);
    }

    @Override
    public ComType visitMain(AbsParser.MainContext ctx) {
        return visitCom((AbsParser.ComContext) ctx.com());
    }

    @Override
    public ComType visitDecl(AbsParser.DeclContext ctx) {

        ExpType varType = TypeUtils.fromString(ctx.TYPE().getText());
        String id = ctx.ID().getText();
        if (typeMap.containsKey(id)) { // already declared variable
            String err = "Variable " + id + " already declared.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new VarDeclarationException(err);
        }
        typeMap.put(id, varType);
        if (ctx.exp() != null) {
            ExpType expType = (ExpType) visit(ctx.exp());
            assert varType != null; // always true
            if (!varType.isCompatible(expType)) {// type mismatch
                String err = "Variable " + id + " cannot be assigned with " + expType.getName() + ".\n"+
                        "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
                throw new TypeMismatchException(err);
            }
        }

        return ComType.INSTANCE;
    }

    @Override
    public ComType visitWhile(AbsParser.WhileContext ctx) {
        visitBoolExp(ctx.exp());

        AbsTS typeSystem = new AbsTS(typeMap);

        return typeSystem.visitCom(ctx.com());
    }

    @Override
    public ComType visitIf(AbsParser.IfContext ctx) {
        visitBoolExp(ctx.exp());

        AbsTS typeSystem = new AbsTS(typeMap);

        return typeSystem.visitCom(ctx.com());
    }

    @Override
    public ComType visitIfElse(AbsParser.IfElseContext ctx) {
        visitBoolExp(ctx.exp());

        AbsTS typeSystem = new AbsTS(typeMap);
        typeSystem.visitCom(ctx.com(0));

        typeSystem = new AbsTS(typeMap);

        return typeSystem.visitCom(ctx.com(1));
    }

    @Override
    public ComType visitAssign(AbsParser.AssignContext ctx) {

        String id = ctx.ID().getText();

        if (!typeMap.containsKey(id)) { // assignment to not declared variable
            String err = "Variable " + id + " assigned but never declared.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine();
            throw new VarDeclarationException(err);
        }

        ExpType varType = typeMap.get(id);
        ExpType expType = (ExpType) visit(ctx.exp());
        if (!varType.isCompatible(expType)) { // type mismatch
            String err = "Variable " + id + " cannot be assigned with " + expType.getName() + ".\n"+
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }

        return ComType.INSTANCE;
    }

    @Override
    public ExpType visitId(AbsParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (!typeMap.containsKey(id)) { // not declared variable
            String err = "Variable " + id + " not declared.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new VarDeclarationException(err);
        }

        return typeMap.get(id);
    }

    @Override
    public ComType visitPrint(AbsParser.PrintContext ctx) {
        visitIntExp(ctx.exp());

        return ComType.INSTANCE;
    }

    @Override
    public SimpleType visitMulDiv(AbsParser.MulDivContext ctx) {
        SimpleType left = visitIntExp(ctx.exp(0));
        SimpleType right = visitIntExp(ctx.exp(1));
        if (!tryToUpcast(left, right)) {
            String err = "Type mismatch: the operation cannot be applied to the given operands.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }
        return left.isCompatible(right) ? left : right;
    }

    @Override
    public SimpleType visitIntVal(AbsParser.IntValContext ctx) {
        return SimpleType.INT;
    }

    @Override
    public SimpleType visitAddSub(AbsParser.AddSubContext ctx) {
        SimpleType left = visitIntExp(ctx.exp(0));
        SimpleType right = visitIntExp(ctx.exp(1));
        if (!tryToUpcast(left, right)) {
            String err = "Type mismatch: the operation cannot be applied to the given operands.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }
        return left.isCompatible(right) ? left : right;
    }

    @Override
    public SimpleType visitLogic(AbsParser.LogicContext ctx) {
        visitBoolExp(ctx.exp(0));
        visitBoolExp(ctx.exp(1));

        return SimpleType.BOOL;
    }

    @Override
    public SimpleType visitNot(AbsParser.NotContext ctx) {
        visitBoolExp(ctx.exp());

        return SimpleType.BOOL;
    }

    @Override
    public SimpleType visitEqExp(AbsParser.EqExpContext ctx) {
        ExpType left = (ExpType) visit(ctx.exp(0));
        ExpType right = (ExpType) visit(ctx.exp(1));

        if (!tryToUpcast(left, right)) {
            String err = "Type mismatch: the operation cannot be applied to the given operands.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }

        return SimpleType.BOOL;
    }

    @Override
    public SimpleType visitBoolVal(AbsParser.BoolValContext ctx) {
        return SimpleType.BOOL;
    }

    @Override
    public SimpleType visitCmpExp(AbsParser.CmpExpContext ctx) {
        SimpleType left = visitIntExp(ctx.exp(0));
        SimpleType right = visitIntExp(ctx.exp(1));

        if (!tryToUpcast(left, right)) {
            String err = "Type mismatch: the operation cannot be applied to the given operands.\n" +
                    "@" + ctx.start.getLine() + ":" + ctx.start.getCharPositionInLine() + "\n";
            throw new TypeMismatchException(err);
        }

        return SimpleType.BOOL;
    }

    @Override
    public ExpType visitParExp(AbsParser.ParExpContext ctx) {
        return (ExpType) visit(ctx.exp());
    }
}
