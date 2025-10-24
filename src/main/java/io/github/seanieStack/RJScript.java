package io.github.seanieStack;

import io.github.seanieStack.interpreter.Interpreter;
import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.util.PrintAST;
import org.antlr.v4.runtime.*;

import java.io.IOException;

public class RJScript {
    public static void main(String[] args) {
        try {
            CharStream inputStream;

            if (args.length > 0) {
                String filePath = args[0];

                if (!filePath.endsWith(".rjs")) {
                    System.err.println("Error: File must have .rjs extension");
                    System.exit(1);
                }

                inputStream = CharStreams.fromFileName(filePath);
            } else {
                String input = "(67 + (85 - 5) + 10 + (-10 * 2)) / 2;";
                inputStream = CharStreams.fromString(input);
            }

            RJScriptLexer lexer = new RJScriptLexer(inputStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            RJScriptParser parser = new RJScriptParser(tokens);
            RJScriptParser.ProgramContext parseTree = parser.program();

            System.out.println("AST:");
            System.out.println(new PrintAST(parseTree));
            System.out.println("\nOutput:");

            Interpreter interpreter = new Interpreter();
            interpreter.visit(parseTree);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
}
