package it.univr.pl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import it.univr.pl.visitor.AbsTS;
import it.univr.pl.visitor.SignAbsIntp;
import it.univr.pl.exception.TypeMismatchException;
import it.univr.pl.exception.VarDeclarationException;

import java.io.IOException;

public class MainAbstractInterpreter {
    public static void main(String[] args) {

        // Sostituisci questo blocco con il tuo sistema di caricamento file/stringhe se necessario
        String programCode = "int x; x = 5; if (x > 0) { x = x - 10; } else { x = 0; }";

        System.out.println("--- INIZIO PIPELINE COMPILATORE ---");

        // 1. Inizializzazione ANTLR
        AbsLexer lexer = new AbsLexer(CharStreams.fromString(programCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AbsParser parser = new AbsParser(tokens);
        AbsParser.MainContext mainCtx = parser.main();

        // 2. FASE 1: Validazione Semantica tramite il Type System concreto esistente
        try {
            AbsTS typeSystem = new AbsTS();
            typeSystem.visit(mainCtx);
            System.out.println("[OK] Controllo dei tipi superato.");
        } catch (TypeMismatchException | VarDeclarationException e) {
            System.err.println("[ERRORE DI TIPO] " + e.getMessage());
            return; // Blocca l'esecuzione se il codice non è tipato correttamente
        }

        // 3. FASE 2: Interpretazione Astratta dei Segni (Analisi Statica)
        System.out.println("\n--- AVVIO ANALISI ASTRATTA DEI SEGNI ---");
        SignAbsMem abstractMem = new SignAbsMem();
        SignAbsIntp signAnalyzer = new SignAbsIntp(abstractMem);

        signAnalyzer.visit(mainCtx);

        // 4. Stampa dei risultati finali raccolti nella memoria astratta al punto fisso
        System.out.println("\n--- RISULTATO STATICO DELLE VARIABILI AL PUNTO FISSO ---");
        System.out.println(abstractMem);
    }
}