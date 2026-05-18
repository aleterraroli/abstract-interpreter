package it.univr.pl;

public class AbsIntp extends AbsBaseVisitor<Integer> {
	@Override
	public Integer visitMain(AbsParser.MainContext ctx) {
        return visitChildren(ctx);
    }
}
