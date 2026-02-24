package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that removes leading and trailing whitespace from a string.
 * Usage: trim(str)
 */
public class TrimFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String str)) {
            throw new RuntimeException("trim() requires a string argument");
        }
        return str.trim();
    }

    @Override
    public String name() {
        return "trim";
    }

    @Override
    public int arity() {
        return 1;
    }
}
