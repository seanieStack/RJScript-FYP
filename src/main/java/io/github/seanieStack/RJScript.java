package io.github.seanieStack;

import io.github.seanieStack.ast.builder.ASTBuilder;
import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.structural.ProgramNode;
import io.github.seanieStack.constants.Constants;
import io.github.seanieStack.constants.ErrorMessages;
import io.github.seanieStack.environments.Environment;
import io.github.seanieStack.errors.ErrorReporter;
import io.github.seanieStack.errors.RJScriptError;
import io.github.seanieStack.interpreter.ASTInterpreter;
import io.github.seanieStack.interpreter.Callable;
import io.github.seanieStack.interpreter.Function;
import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.util.ASTPrinter;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main entry point for the RJScript interpreter.
 *
 * Debug flags (can be combined):
 *   --ast  / -a   Print the AST before execution
 *   --tokens / -t  Print the lexer token stream
 *   --trace / -T   Trace each interpreter visit call
 *   --env  / -e   Dump variables and user functions after execution
 */
public class RJScript {

    private static final Logger logger = Logger.getLogger(RJScript.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler(Constants.LOG_FILE_NAME, true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            // fallback: log to stderr if file can't be opened
        }
    }

    private record DebugFlags(boolean showAst, boolean showTokens, boolean trace, boolean showEnv) {}

    /**
     * Starts the interpreter. Runs the file if provided, otherwise starts REPL.
     * Flags may appear anywhere in the argument list.
     *
     * @param args command-line arguments; optional flags and .rjs file path
     */
    public static void main(String[] args) {
        String filePath = null;
        boolean showAst = false, showTokens = false, trace = false, showEnv = false;

        for (String arg : args) {
            switch (arg) {
                case "--ast",    "-a" -> showAst    = true;
                case "--tokens", "-t" -> showTokens = true;
                case "--trace",  "-T" -> trace      = true;
                case "--env",    "-e" -> showEnv    = true;
                default -> filePath = arg;
            }
        }

        DebugFlags flags = new DebugFlags(showAst, showTokens, trace, showEnv);

        if (filePath != null) {
            runFile(filePath, flags);
        } else {
            System.err.println("Usage: rjscript [flags] <file.rjs>");
            System.exit(Constants.EXIT_CODE_ERROR);
        }
    }

    /**
     * Executes a .rjs script file.
     *
     * @param filePath path to the .rjs file to execute
     * @param flags active debug flags
     */
    private static void runFile(String filePath, DebugFlags flags) {
        try {
            if (!filePath.endsWith(Constants.FILE_EXTENSION)) {
                System.err.println(ErrorMessages.ERROR_INVALID_FILE_EXTENSION);
                System.exit(Constants.EXIT_CODE_ERROR);
            }

            CharStream inputStream = CharStreams.fromFileName(filePath);
            RJScriptLexer lexer = new RJScriptLexer(inputStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            if (flags.showTokens()) {
                tokens.fill();
                System.out.println(Constants.DEBUG_TOKENS_LABEL);
                for (Token token : tokens.getTokens()) {
                    if (token.getType() != Token.EOF) {
                        String name = lexer.getVocabulary().getSymbolicName(token.getType());
                        System.out.printf("  %-20s '%s'  (%d:%d)%n",
                            name, token.getText(), token.getLine(), token.getCharPositionInLine());
                    }
                }
                System.out.println();
                tokens.seek(0);
            }

            RJScriptParser parser = new RJScriptParser(tokens);
            RJScriptParser.ProgramContext parseTree = parser.program();

            ASTBuilder astBuilder = new ASTBuilder();
            ASTNode ast = astBuilder.visit(parseTree);

            if (flags.showAst()) {
                ASTPrinter astPrinter = new ASTPrinter();
                System.out.println(Constants.OUTPUT_AST_LABEL);
                System.out.println(astPrinter.visit((ProgramNode) ast));
                System.out.println();
            }

            ASTInterpreter interpreter = new ASTInterpreter();
            interpreter.setTraceEnabled(flags.trace());
            ast.accept(interpreter);

            if (flags.showEnv()) {
                printEnv(interpreter.getEnv());
            }

        } catch (RJScriptError e) {
            System.err.println(ErrorReporter.format(e, filePath));
            System.exit(Constants.EXIT_CODE_ERROR);
        } catch (IOException e) {
            System.err.println(ErrorMessages.ERROR_READING_FILE_PREFIX + e.getMessage());
            System.exit(Constants.EXIT_CODE_ERROR);
        } catch (Exception e) {
            logger.severe(Constants.INTERNAL_ERROR_LOG_PREFIX + e.getMessage());
            logger.throwing(RJScript.class.getName(), "runFile", e);
            System.err.println(Constants.INTERNAL_ERROR_USER_MESSAGE);
            System.exit(Constants.EXIT_CODE_ERROR);
        }
    }

    private static void printEnv(Environment env) {
        System.out.println(Constants.DEBUG_ENV_VARIABLES_LABEL);
        env.getVariables().entrySet().stream()
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(e -> System.out.println("  " + e.getKey() + " = " + e.getValue()));

        System.out.println(Constants.DEBUG_ENV_FUNCTIONS_LABEL);
        env.getFunctions().entrySet().stream()
            .filter(e -> e.getValue() instanceof Function)
            .sorted(java.util.Map.Entry.comparingByKey())
            .forEach(e -> {
                Function fn = (Function) e.getValue();
                System.out.println("  " + e.getKey() + "(" + String.join(", ", fn.parameters()) + ")");
            });
    }
}
