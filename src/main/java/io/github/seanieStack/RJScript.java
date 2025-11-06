package io.github.seanieStack;

import io.github.seanieStack.ast.ASTBuilder;
import io.github.seanieStack.ast.ASTNode;
import io.github.seanieStack.ast.ProgramNode;
import io.github.seanieStack.interpreter.ASTInterpreter;
import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.util.ASTPrinter;
import org.antlr.v4.runtime.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RJScript {
    public static void main(String[] args) {
        if (args.length > 0) {
            runFile(args[0]);
        } else {
            runRepl();
        }
    }

    private static void runFile(String filePath) {
        try {
            if (!filePath.endsWith(".rjs")) {
                System.err.println("Error: File must have .rjs extension");
                System.exit(1);
            }

            //lexer
            CharStream inputStream = CharStreams.fromFileName(filePath);
            RJScriptLexer lexer = new RJScriptLexer(inputStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            //parser
            RJScriptParser parser = new RJScriptParser(tokens);
            RJScriptParser.ProgramContext parseTree = parser.program();

            //to ast
            ASTBuilder astBuilder = new ASTBuilder();
            ASTNode ast = astBuilder.visit(parseTree);

            //print ast
            System.out.println("AST:");
            ASTPrinter astPrinter = new ASTPrinter();
            System.out.println(astPrinter.visit((ProgramNode) ast));
            System.out.println("Output:");

            //interpreter
            ASTInterpreter interpreter = new ASTInterpreter();
            ast.accept(interpreter);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runRepl() {
        System.out.println("RJScript REPL");
        System.out.println("Type 'exit' to quit");
        System.out.println();

        ASTInterpreter interpreter = new ASTInterpreter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder multiLineInput = new StringBuilder();

        //keep looping till exit
        while (true) {
            try {
                if (multiLineInput.isEmpty()) {
                    System.out.print("> ");
                } else {
                    System.out.print("");
                }

                String line = reader.readLine();

                if (line == null) {
                    break;
                }

                line = line.trim();

                if (line.equals("exit")) {
                    break;
                }

                if (line.isEmpty()) {
                    continue;
                }

                multiLineInput.append(line).append("\n");

                if (line.endsWith(";")) {
                    String input = multiLineInput.toString();
                    multiLineInput.setLength(0);

                    try {
                        CharStream inputStream = CharStreams.fromString(input);
                        RJScriptLexer lexer = new RJScriptLexer(inputStream);
                        CommonTokenStream tokens = new CommonTokenStream(lexer);

                        RJScriptParser parser = new RJScriptParser(tokens);
                        RJScriptParser.ProgramContext parseTree = parser.program();

                        if (parser.getNumberOfSyntaxErrors() == 0) {
                            ASTBuilder astBuilder = new ASTBuilder();
                            ASTNode ast = astBuilder.visit(parseTree);

                            ast.accept(interpreter);
                        } else {
                            System.err.println("Syntax error in input");
                        }
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }

            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
                break;
            }
        }
    }
}
