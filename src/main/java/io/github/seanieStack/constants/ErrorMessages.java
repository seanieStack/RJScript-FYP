package io.github.seanieStack.constants;

/**
 * Error message constants for the RJScript interpreter.
 * Centralizes all error messages for consistency and maintainability.
 */
public final class ErrorMessages {

    private ErrorMessages() {
        throw new AssertionError("ErrorMessages class should not be instantiated");
    }

    // File handling errors
    public static final String ERROR_INVALID_FILE_EXTENSION = "Error: File must have .rjs extension";
    public static final String ERROR_READING_FILE_PREFIX = "Error reading file: ";
    public static final String ERROR_READING_INPUT_PREFIX = "Error reading input: ";

    // Parsing errors
    public static final String ERROR_SYNTAX_ERROR = "Syntax error in input";

    // Runtime errors operators
    public static final String ERROR_DIVISION_BY_ZERO = "Division by zero";

    // Runtime errors - variables and functions
    public static final String ERROR_UNDEFINED_VARIABLE = "Undefined variable: ";
    public static final String ERROR_UNDEFINED_FUNCTION = "Undefined function: ";
    public static final String ERROR_FUNCTION_ARGUMENT_MISMATCH = "Function %s expects %d arguments but got %d";

    // Type conversion errors
    public static final String ERROR_NULL_TO_INTEGER = "Cannot convert null to integer";
    public static final String ERROR_NULL_TO_BOOLEAN = "Cannot convert null to boolean";
    public static final String ERROR_CANNOT_CONVERT_TO_INTEGER = "Cannot convert %s to integer";
    public static final String ERROR_CANNOT_CONVERT_TO_BOOLEAN = "Cannot convert %s to boolean";

    // Error prefixes
    public static final String ERROR_PREFIX = "Error: ";
}