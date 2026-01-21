package io.github.seanieStack.constants;

/**
 * Central constants for the RJScript interpreter.
 * Contains file extensions, exit codes, REPL settings, and output labels.
 */
public final class Constants {

    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }

    // File handling
    public static final String FILE_EXTENSION = ".rjs";

    // Exit codes
    public static final int EXIT_CODE_SUCCESS = 0;
    public static final int EXIT_CODE_ERROR = 1;

    // REPL configuration
    public static final String REPL_WELCOME_MESSAGE = "RJScript REPL";
    public static final String REPL_EXIT_INSTRUCTIONS = "Type 'exit' to quit";
    public static final String REPL_PROMPT = "> ";
    public static final String REPL_EXIT_COMMAND = "exit";
    public static final String STATEMENT_TERMINATOR = ";";

    // Output labels
    public static final String OUTPUT_AST_LABEL = "AST:";
    public static final String OUTPUT_EXECUTION_LABEL = "Output:";

    // Common characters
    public static final String NEWLINE = "\n";

    // Type conversion values
    public static final int TRUE_INTEGER_VALUE = 1;
    public static final int FALSE_INTEGER_VALUE = 0;
    public static final double TRUE_FLOAT_VALUE = 1.0;
    public static final double FALSE_FLOAT_VALUE = 0.0;
}