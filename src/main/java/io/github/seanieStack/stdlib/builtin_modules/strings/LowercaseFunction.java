package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that converts a string to lowercase.
 * Usage: lowercase(str)
 */
public class LowercaseFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String str)) {
            throw new RuntimeException("lowercase() requires a string argument");
        }
        return str.toLowerCase();
    }

    @Override
    public String name() {
        return "lowercase";
    }

    @Override
    public int arity() {
        return 1;
    }
}
