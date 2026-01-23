package io.github.seanieStack.interpreter;

import io.github.seanieStack.constants.Constants;
import io.github.seanieStack.constants.ErrorMessages;

/**
 * Utility class for converting values between different types used in the interpreter.
 * Provides type coercion rules for integers, doubles, and booleans.
 */
public class TypeConverter {

    /**
     * Converts a value to an integer using type coercion rules. Integers are returned
     * as-is, booleans are converted to 1 (true) or 0 (false), floats are rounded. Other types cause an error.
     *
     * @param value the value to convert to an integer
     * @return the integer representation of the value
     * @throws RuntimeException if the value cannot be converted to an integer
     */
    public static int toInt(Object value) {
        return switch (value) {
            case null -> throw new RuntimeException(ErrorMessages.ERROR_NULL_TO_INTEGER);
            case Integer i -> i;
            case Double d -> (int) Math.round(d);
            case Boolean b -> b ? Constants.TRUE_INTEGER_VALUE : Constants.FALSE_INTEGER_VALUE;
            default -> throw new RuntimeException(String.format(ErrorMessages.ERROR_CANNOT_CONVERT_TO_INTEGER, value));
        };
    }

    /**
     * Converts a value to a boolean using type coercion rules. Booleans are returned
     * as-is, non-zero integers are converted to true, and zero is converted to false.
     * Other types cause an error.
     *
     * @param value the value to convert to a boolean
     * @return the boolean representation of the value
     * @throws RuntimeException if the value cannot be converted to a boolean
     */
    public static boolean toBoolean(Object value) {
        return switch (value) {
            case null -> throw new RuntimeException(ErrorMessages.ERROR_NULL_TO_BOOLEAN);
            case Boolean b -> b;
            case Integer i -> i != 0;
            case Double d -> d != 0.0;
            default -> throw new RuntimeException(String.format(ErrorMessages.ERROR_CANNOT_CONVERT_TO_BOOLEAN, value));
        };
    }

    /**
     * Converts a value to a double using type coercion rules. Floats are returned
     * as-is, integers are converted into floats with .0, booleans are 1.0 (true) or
     * 0.0 (false), other types cause an error.
     *
     * @param value the value to convert to a double
     * @return the double representation of the value
     * @throws RuntimeException if the value cannot be converted to a double
     */
    public static double toDouble(Object value) {
        return switch (value) {
            case null -> throw new RuntimeException(ErrorMessages.ERROR_NULL_TO_FLOAT);
            case Double d -> d;
            case Integer i -> (double) i;
            case Boolean b -> b ? Constants.TRUE_FLOAT_VALUE : Constants.FALSE_FLOAT_VALUE;
            default -> throw new RuntimeException(String.format(ErrorMessages.ERROR_CANNOT_CONVERT_FLOAT, value));
        };
    }
}