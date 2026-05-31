package it.univr.pl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import it.univr.pl.visitor.AbsTS;
import it.univr.pl.visitor.SignAbsIntp;
import it.univr.pl.exception.TypeMismatchException;
import it.univr.pl.exception.VarDeclarationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainAbstractInterpreter {
    public static void main(String[] args) {
        String examplesDirPath = "examples";
        File folder = new File(examplesDirPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Errore: La cartella '" + examplesDirPath + "' non esiste nella root del progetto!");
            return;
        }

        File[] testFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (testFiles == null || testFiles.length == 0) {
            System.out.println("Nessun file .txt trovato nella cartella '" + examplesDirPath + "'");
            return;
        }

        for (File file : testFiles) {
            System.out.println("Analisi del file: " + file.getName());

            try {
                String programCode = new String(Files.readAllBytes(Paths.get(file.getPath())));

                AbsLexer lexer = new AbsLexer(CharStreams.fromString(programCode));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                AbsParser parser = new AbsParser(tokens);
                AbsParser.MainContext mainCtx = parser.main();

                System.out.print("[1/2] Esecuzione Type System... ");
                try {
                    AbsTS typeSystem = new AbsTS();
                    typeSystem.visit(mainCtx);
                    System.out.println("OK (Tipi validi)");
                } catch (TypeMismatchException | VarDeclarationException e) {
                    System.out.println("FALLITO!");
                    System.err.println("   >> Errore di Tipo Statico: " + e.getMessage().trim());
                    System.out.println("[INFO] Analisi astratta interrotta per violazione di tipo.");
                    continue;
                }

                System.out.println("[2/2] Avvio Analisi Astratta dei Segni...");
                SignAbsMem abstractMem = new SignAbsMem();
                SignAbsIntp signAnalyzer = new SignAbsIntp(abstractMem);

                signAnalyzer.visit(mainCtx);

                System.out.println("\n>> STATO STATICO FINALE DELLE VARIABILI:");
                System.out.println("   " + abstractMem);

            } catch (IOException e) {
                System.err.println("Errore nella lettura del file " + file.getName() + ": " + e.getMessage());
            }
        }
    }
}