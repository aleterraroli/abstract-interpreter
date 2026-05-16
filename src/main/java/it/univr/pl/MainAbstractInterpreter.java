package it.univr.pl;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MainAbstractInterpreter {

    public static void main(String[] args) {

        String prog = "x = 3;";
        CharStream cs = CharStreams.fromString(prog);
        AbsLexer lexer = new AbsLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AbsParser parser = new AbsParser(tokens);
        ParseTree tree = parser.main();
        System.out.println(tree.toStringTree(parser));

    }
}