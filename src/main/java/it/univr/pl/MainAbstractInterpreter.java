package it.univr.pl;

import it.univr.pl.ast.Program;
import it.univr.pl.visitor.ASTBuilderVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class MainAbstractInterpreter {

    public static void main(String[] args)
            throws Exception {

        String source = """

                int x = 3;

                x = x + 1;

                if (x > 0) {
                    print x;
                }

                """;
        CharStream chars = CharStreams.fromString(source);
        AbsLexer lexer = new AbsLexer(chars);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AbsParser parser = new AbsParser(tokens);
        ParseTree tree = parser.main();
        System.out.println("===== PARSE TREE =====");
        System.out.println(tree.toStringTree(parser));
        ASTBuilderVisitor builder = new ASTBuilderVisitor();
        Program ast = (Program) builder.visit(tree);
        System.out.println("\n===== AST =====");
        System.out.println(ast);
    }
}