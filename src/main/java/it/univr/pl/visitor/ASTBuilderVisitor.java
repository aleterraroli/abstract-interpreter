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


}