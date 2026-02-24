package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that extracts a substring from a string.
 * Usage: substring(str, start, end)
 */
public class SubstringFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object startVal = arguments.get(1);
        Object endVal = arguments.get(2);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("substring() requires a string as first argument");
        }
        if (!(startVal instanceof Long) && !(startVal instanceof Integer)) {
            throw new RuntimeException("substring() requires an integer as second argument");
        }
        if (!(endVal instanceof Long) && !(endVal instanceof Integer)) {
            throw new RuntimeException("substring() requires an integer as third argument");
        }

        int start = ((Number) startVal).intValue();
        int end = ((Number) endVal).intValue();

        if (start < 0 || end > str.length() || start > end) {
            throw new RuntimeException("substring() indices out of bounds: start=" + start + ", end=" + end + ", length=" + str.length());
        }

        return str.substring(start, end);
    }

    @Override
    public String name() {
        return "substring";
    }

    @Override
    public int arity() {
        return 3;
    }
}
