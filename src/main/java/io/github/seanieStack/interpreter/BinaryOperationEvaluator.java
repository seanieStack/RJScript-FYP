package io.github.seanieStack.interpreter;

import io.github.seanieStack.ast.expressions.BinaryOpNode;
import io.github.seanieStack.constants.ErrorMessages;

/**
 * Evaluates binary operations between two operands. Handles arithmetic operations
 * (addition, subtraction, multiplication, division) and comparison operations
 * (less than, greater than, equality, etc.) for integers, floats, and strings.
 */
public class BinaryOperationEvaluator {

    /**
     * Evaluates a binary operation with the given left and right operands.
     *
     * @param operator the binary operator to apply
     * @param left the left operand
     * @param right the right operand
     * @return the result of the binary operation (Integer, Double, Boolean, or String)
     * @throws RuntimeException if division by zero occurs
     */
    public Object evaluate(BinaryOpNode.Operator operator, Object left, Object right) {
        boolean isString = (left instanceof String || right instanceof String);
        boolean isBoolean = (left instanceof Boolean || right instanceof Boolean);
        boolean isFloat = (left instanceof Double || right instanceof Double);

        return switch (operator) {
            case ADD -> evaluateAddition(left, right, isString, isFloat);
            case SUBTRACT -> evaluateSubtraction(left, right, isFloat);
            case MULTIPLY -> evaluateMultiplication(left, right, isFloat);
            case DIVIDE -> evaluateDivision(left, right, isFloat);
            case LESS_THAN -> evaluateLessThan(left, right, isFloat);
            case GREATER_THAN -> evaluateGreaterThan(left, right, isFloat);
            case LESS_EQUAL -> evaluateLessEqual(left, right, isFloat);
            case GREATER_EQUAL -> evaluateGreaterEqual(left, right, isFloat);
            case EQUAL -> evaluateEqual(left, right, isString, isBoolean, isFloat);
            case NOT_EQUAL -> evaluateNotEqual(left, right, isString, isBoolean, isFloat);
        };
    }

    /**
     * Evaluates addition. Supports string concatenation and numeric addition.
     */
    private Object evaluateAddition(Object left, Object right, boolean isString, boolean isFloat) {
        if (isString) {
            return String.valueOf(left) + right;
        }
        else if (isFloat) {
            return TypeConverter.toDouble(left) + TypeConverter.toDouble(right);
        }
        else {
            return TypeConverter.toInt(left) + TypeConverter.toInt(right);
        }
    }

    /**
     * Evaluates subtraction for integers and floats.
     */
    private Object evaluateSubtraction(Object left, Object right, boolean isFloat) {
        if (isFloat) {
            return TypeConverter.toDouble(left) - TypeConverter.toDouble(right);
        } else {
            return TypeConverter.toInt(left) - TypeConverter.toInt(right);
        }
    }

    /**
     * Evaluates multiplication for integers and floats.
     */
    private Object evaluateMultiplication(Object left, Object right, boolean isFloat) {
        if (isFloat) {
            return TypeConverter.toDouble(left) * TypeConverter.toDouble(right);
        } else {
            return TypeConverter.toInt(left) * TypeConverter.toInt(right);
        }
    }

    /**
     * Evaluates division for integers and floats. Checks for division by zero.
     */
    private Object evaluateDivision(Object left, Object right, boolean isFloat) {
        if (isFloat) {
            double rightVal = TypeConverter.toDouble(right);
            if (rightVal == 0.0) {
                throw new RuntimeException(ErrorMessages.ERROR_DIVISION_BY_ZERO);
            }
            return TypeConverter.toDouble(left) / rightVal;
        } else {
            int rightVal = TypeConverter.toInt(right);
            if (rightVal == 0) {
                throw new RuntimeException(ErrorMessages.ERROR_DIVISION_BY_ZERO);
            }
            return TypeConverter.toInt(left) / rightVal;
        }
    }

    /**
     * Evaluates less than comparison for integers and floats.
     */
    private boolean evaluateLessThan(Object left, Object right, boolean isFloat) {
        return isFloat ? TypeConverter.toDouble(left) < TypeConverter.toDouble(right)
                       : TypeConverter.toInt(left) < TypeConverter.toInt(right);
    }

    /**
     * Evaluates greater than comparison for integers and floats.
     */
    private boolean evaluateGreaterThan(Object left, Object right, boolean isFloat) {
        return isFloat ? TypeConverter.toDouble(left) > TypeConverter.toDouble(right)
                       : TypeConverter.toInt(left) > TypeConverter.toInt(right);
    }

    /**
     * Evaluates less than or equal comparison for integers and floats.
     */
    private boolean evaluateLessEqual(Object left, Object right, boolean isFloat) {
        return isFloat ? TypeConverter.toDouble(left) <= TypeConverter.toDouble(right)
                       : TypeConverter.toInt(left) <= TypeConverter.toInt(right);
    }

    /**
     * Evaluates greater than or equal comparison for integers and floats.
     */
    private boolean evaluateGreaterEqual(Object left, Object right, boolean isFloat) {
        return isFloat ? TypeConverter.toDouble(left) >= TypeConverter.toDouble(right)
                       : TypeConverter.toInt(left) >= TypeConverter.toInt(right);
    }

    /**
     * Evaluates equality comparison. Supports strings, booleans, floats, and integers.
     */
    private boolean evaluateEqual(Object left, Object right, boolean isString, boolean isBoolean, boolean isFloat) {
        if (isString) {
            return String.valueOf(left).equals(String.valueOf(right));
        }
        else if (isBoolean) {
            return TypeConverter.toBoolean(left) == TypeConverter.toBoolean(right);
        }
        return isFloat ? TypeConverter.toDouble(left) == TypeConverter.toDouble(right)
                       : TypeConverter.toInt(left) == TypeConverter.toInt(right);
    }

    /**
     * Evaluates inequality comparison. Supports strings, booleans, floats, and integers.
     */
    private boolean evaluateNotEqual(Object left, Object right, boolean isString, boolean isBoolean, boolean isFloat) {
        if (isString) {
            return !String.valueOf(left).equals(String.valueOf(right));
        }
        else if (isBoolean) {
            return TypeConverter.toBoolean(left) != TypeConverter.toBoolean(right);
        }
        return isFloat ? TypeConverter.toDouble(left) != TypeConverter.toDouble(right)
                       : TypeConverter.toInt(left) != TypeConverter.toInt(right);
    }
}