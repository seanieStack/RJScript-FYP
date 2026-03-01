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

    // Runtime errors operators
    public static final String ERROR_DIVISION_BY_ZERO = "Division by zero";

    // Runtime errors - variables and functions
    public static final String ERROR_UNDEFINED_VARIABLE = "Undefined variable: ";
    public static final String ERROR_UNDEFINED_FUNCTION = "Undefined function: ";
    public static final String ERROR_FUNCTION_ARGUMENT_MISMATCH = "Function %s expects %d arguments but got %d";
    public static final String ERROR_NATIVE_FUNCTION_ARGUMENT_MISMATCH = "Native function %s expects %d arguments but got %d";

    // Type conversion errors
    public static final String ERROR_NULL_TO_INTEGER = "Cannot convert null to integer";
    public static final String ERROR_NULL_TO_BOOLEAN = "Cannot convert null to boolean";
    public static final String ERROR_NULL_TO_FLOAT = "Cannot convert null to double";
    public static final String ERROR_CANNOT_CONVERT_TO_INTEGER = "Cannot convert %s to integer";
    public static final String ERROR_CANNOT_CONVERT_TO_BOOLEAN = "Cannot convert %s to boolean";
    public static final String ERROR_CANNOT_CONVERT_FLOAT = "Cannot convert %s to float";


    // Parse/syntax errors
    public static final String ERROR_UNKNOWN_COMPARISON_OPERATOR = "Unknown comparison operator: ";
    public static final String ERROR_UNKNOWN_PRIMARY_EXPRESSION = "Unknown primary expression";

    // Runtime errors - array operations
    public static final String ERROR_CANNOT_INDEX_NON_ARRAY = "Cannot index into non-array value of type ";
    public static final String ERROR_ARRAY_INDEX_NOT_INTEGER = "Array index must be an integer, got ";
    public static final String ERROR_ARRAY_INDEX_OUT_OF_BOUNDS = "Array index out of bounds: %d for array of length %d";
    public static final String ERROR_UNKNOWN_UNARY_OPERATOR = "Unknown unary operator: ";

    // Runtime errors - environment
    public static final String ERROR_VARIABLE_NOT_FOUND = "Variable %s not found";

    // Error prefixes
    public static final String ERROR_PREFIX = "Error: ";
}