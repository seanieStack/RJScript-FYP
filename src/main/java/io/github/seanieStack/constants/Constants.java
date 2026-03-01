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
    public static final int EXIT_CODE_ERROR = 1;

    // Output labels
    public static final String OUTPUT_AST_LABEL = "AST:";

    // Logging
    public static final String LOG_FILE_NAME = "rjscript.log";
    public static final String INTERNAL_ERROR_LOG_PREFIX = "Internal error: ";
    public static final String INTERNAL_ERROR_USER_MESSAGE = "An internal error occurred. See rjscript.log for details.";

    // Debug output labels
    public static final String DEBUG_TOKENS_LABEL = "Tokens:";
    public static final String DEBUG_ENV_VARIABLES_LABEL = "\n[Env] Variables:";
    public static final String DEBUG_ENV_FUNCTIONS_LABEL = "[Env] User Functions:";

    // Trace prefix
    public static final String TRACE_PREFIX = "[trace] ";

    // Type conversion values
    public static final int TRUE_INTEGER_VALUE = 1;
    public static final int FALSE_INTEGER_VALUE = 0;
    public static final double TRUE_FLOAT_VALUE = 1.0;
    public static final double FALSE_FLOAT_VALUE = 0.0;
}