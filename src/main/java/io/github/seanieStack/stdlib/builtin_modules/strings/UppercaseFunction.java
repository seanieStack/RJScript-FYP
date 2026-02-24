package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that converts a string to uppercase.
 * Usage: uppercase(str)
 */
public class UppercaseFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String str)) {
            throw new RuntimeException("uppercase() requires a string argument");
        }
        return str.toUpperCase();
    }

    @Override
    public String name() {
        return "uppercase";
    }

    @Override
    public int arity() {
        return 1;
    }
}
