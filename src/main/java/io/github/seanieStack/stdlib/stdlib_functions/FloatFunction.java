package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that converts a value to a float.
 * Usage: float(value)
 * Supports conversion from int, string, and boolean.
 */
public class FloatFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();

        if (value instanceof Double) {
            return value;
        } else if (value instanceof Integer i) {
            return i.doubleValue();
        } else if (value instanceof String s) {
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cannot convert string '" + s + "' to float");
            }
        } else if (value instanceof Boolean b) {
            return b ? 1.0 : 0.0;
        }

        throw new RuntimeException("Cannot convert " + TypeUtils.getTypeName(value) + " to float");
    }

    @Override
    public String name() {
        return "float";
    }

    @Override
    public int arity() {
        return 1;
    }
}
