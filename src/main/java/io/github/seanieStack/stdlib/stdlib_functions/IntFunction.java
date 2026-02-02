package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that converts a value to an integer.
 * Usage: int(value)
 * Supports conversion from float, string, and boolean.
 */
public class IntFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();

        if (value instanceof Integer) {
            return value;
        } else if (value instanceof Double d) {
            return d.intValue();
        } else if (value instanceof String s) {
            try {
                if (s.contains(".")) {
                    return (int) Double.parseDouble(s);
                }
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cannot convert string '" + s + "' to int");
            }
        } else if (value instanceof Boolean b) {
            return b ? 1 : 0;
        }

        throw new RuntimeException("Cannot convert " + TypeUtils.getTypeName(value) + " to int");
    }

    @Override
    public String name() {
        return "int";
    }

    @Override
    public int arity() {
        return 1;
    }
}
