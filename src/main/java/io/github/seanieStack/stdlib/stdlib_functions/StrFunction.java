package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that converts a value to a string.
 * Usage: str(value)
 * Works with any value type.
 */
public class StrFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (value == null) {
            return "null";
        }
        return String.valueOf(value);
    }

    @Override
    public String name() {
        return "str";
    }

    @Override
    public int arity() {
        return 1;
    }
}
