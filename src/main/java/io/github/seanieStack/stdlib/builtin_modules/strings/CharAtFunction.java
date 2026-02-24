package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that returns the character at a given index.
 * Usage: charAt(str, index)
 */
public class CharAtFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object indexVal = arguments.get(1);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("charAt() requires a string as first argument");
        }
        if (!(indexVal instanceof Long) && !(indexVal instanceof Integer)) {
            throw new RuntimeException("charAt() requires an integer as second argument");
        }

        int index = ((Number) indexVal).intValue();

        if (index < 0 || index >= str.length()) {
            throw new RuntimeException("charAt() index out of bounds: " + index + " for string of length " + str.length());
        }

        return String.valueOf(str.charAt(index));
    }

    @Override
    public String name() {
        return "charAt";
    }

    @Override
    public int arity() {
        return 2;
    }
}
