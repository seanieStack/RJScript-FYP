package io.github.seanieStack.stdlib;

/**
 * Utility methods for type operations in native functions.
 */
public final class TypeUtils {

    private TypeUtils() {}

    /**
     * Returns the RJScript type name for a value.
     */
    public static String getTypeName(Object value) {
        return switch (value) {
            case null -> "null";
            case Integer i -> "int";
            case Double v -> "float";
            case Boolean b -> "bool";
            case String s -> "string";
            default -> value.getClass().getSimpleName();
        };
    }

    /**
     * Converts a value to double, throwing if not numeric.
     */
    public static double toDouble(Object value, String functionName) {
        if (value instanceof Integer i) {
            return i.doubleValue();
        } else if (value instanceof Double d) {
            return d;
        }
        throw new RuntimeException(functionName + " requires numeric arguments, got " + getTypeName(value));
    }

    /**
     * Converts a value to int, throwing if not numeric.
     */
    public static int toInt(Object value, String functionName) {
        if (value instanceof Integer i) {
            return i;
        } else if (value instanceof Double d) {
            return d.intValue();
        }
        throw new RuntimeException(functionName + " requires numeric arguments, got " + getTypeName(value));
    }

    /**
     * Requires a numeric value, throwing with context if not.
     */
    public static void requireNumeric(Object value, String functionName) {
        if (!(value instanceof Integer) && !(value instanceof Double)) {
            throw new RuntimeException(functionName + " requires a numeric argument, got " + getTypeName(value));
        }
    }

    /**
     * Requires a string value, throwing with context if not.
     */
    public static void requireString(Object value, String functionName) {
        if (!(value instanceof String)) {
            throw new RuntimeException(functionName + " requires a string argument, got " + getTypeName(value));
        }
    }
}
