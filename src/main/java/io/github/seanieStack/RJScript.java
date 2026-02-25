package io.github.seanieStack;

import io.github.seanieStack.ast.builder.ASTBuilder;
import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.structural.ProgramNode;
import io.github.seanieStack.constants.Constants;
import io.github.seanieStack.constants.ErrorMessages;
import io.github.seanieStack.errors.ErrorReporter;
import io.github.seanieStack.errors.RJScriptError;
import io.github.seanieStack.interpreter.ASTInterpreter;
import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.util.ASTPrinter;
import org.antlr.v4.runtime.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main entry point for the RJScript interpreter.
 * Supports file execution and REPL mode.
 */
public class RJScript {

    private static final Logger logger = Logger.getLogger(RJScript.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler("rjscript.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            // fallback: log to stderr if file can't be opened
        }
    }

    /**
     * Starts the interpreter. Runs the file if provided, otherwise starts REPL.
     *
     * @param args command-line arguments; optional .rjs file path
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            runFile(args[0]);
        } else {
            runRepl();
        }
    }

    /**
     * Executes a .rjs script file.
     * Reads the file, parses it, builds an AST, prints the AST structure, and interprets it.
     *
     * @param filePath path to the .rjs file to execute
     */
    private static void runFile(String filePath) {
        try {
            if (!filePath.endsWith(Constants.FILE_EXTENSION)) {
                System.err.println(ErrorMessages.ERROR_INVALID_FILE_EXTENSION);
                System.exit(Constants.EXIT_CODE_ERROR);
            }

            CharStream inputStream = CharStreams.fromFileName(filePath);
            RJScriptLexer lexer = new RJScriptLexer(inputStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            RJScriptParser parser = new RJScriptParser(tokens);
            RJScriptParser.ProgramContext parseTree = parser.program();

            ASTBuilder astBuilder = new ASTBuilder();
            ASTNode ast = astBuilder.visit(parseTree);

            System.out.println(Constants.OUTPUT_AST_LABEL);
            ASTPrinter astPrinter = new ASTPrinter();
            System.out.println(astPrinter.visit((ProgramNode) ast));
            System.out.println(Constants.OUTPUT_EXECUTION_LABEL);

            ASTInterpreter interpreter = new ASTInterpreter();
            ast.accept(interpreter);

        } catch (RJScriptError e) {
            System.err.println(ErrorReporter.format(e, filePath));
            System.exit(Constants.EXIT_CODE_ERROR);
        } catch (IOException e) {
            System.err.println(ErrorMessages.ERROR_READING_FILE_PREFIX + e.getMessage());
            System.exit(Constants.EXIT_CODE_ERROR);
        } catch (Exception e) {
            logger.severe("Internal error: " + e.getMessage());
            logger.throwing(RJScript.class.getName(), "runFile", e);
            System.err.println("An internal error occurred. See rjscript.log for details.");
            System.exit(Constants.EXIT_CODE_ERROR);
        }
    }

    /**
     * Runs the interactive REPL (Read-Eval-Print Loop).
     * Statements must end with a semicolon to be executed. Type 'exit' to quit.
     */
    private static void runRepl() {
        System.out.println(Constants.REPL_WELCOME_MESSAGE);
        System.out.println(Constants.REPL_EXIT_INSTRUCTIONS);
        System.out.println();

        ASTInterpreter interpreter = new ASTInterpreter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder multiLineInput = new StringBuilder();

        while (true) {
            try {
                if (multiLineInput.isEmpty()) {
                    System.out.print(Constants.REPL_PROMPT);
                }

                String line = reader.readLine();

                if (line == null) {
                    break;
                }

                line = line.trim();

                if (line.equals(Constants.REPL_EXIT_COMMAND)) {
                    break;
                }

                if (line.isEmpty()) {
                    continue;
                }

                multiLineInput.append(line).append(Constants.NEWLINE);

                if (line.endsWith(Constants.STATEMENT_TERMINATOR)) {
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
                            System.err.println(ErrorMessages.ERROR_SYNTAX_ERROR);
                        }
                    } catch (RJScriptError e) {
                        System.err.println(ErrorReporter.format(e));
                    } catch (Exception e) {
                        logger.severe("Internal error: " + e.getMessage());
                        logger.throwing(RJScript.class.getName(), "runRepl", e);
                        System.err.println("An internal error occurred. See rjscript.log for details.");
                    }
                }

            } catch (IOException e) {
                System.err.println(ErrorMessages.ERROR_READING_INPUT_PREFIX + e.getMessage());
                break;
            }
        }
    }
}
