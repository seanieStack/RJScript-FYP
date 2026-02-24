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

        switch (value) {
            case Integer i -> {
                return value;
            }
            case Double d -> {
                if (d > Integer.MAX_VALUE || d < Integer.MIN_VALUE) {
                    throw new RuntimeException("Cannot convert " + d + " to int: value out of range");
                }
                return d.intValue();
            }
            case String s -> {
                try {
                    if (s.contains(".")) {
                        return (int) Double.parseDouble(s);
                    }
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Cannot convert string '" + s + "' to int");
                }
            }
            case Boolean b -> {
                return b ? 1 : 0;
            }
            case null, default -> {
            }
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
