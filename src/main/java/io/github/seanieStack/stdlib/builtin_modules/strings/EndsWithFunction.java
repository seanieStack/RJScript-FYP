package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that checks if a string ends with a suffix.
 * Usage: endsWith(str, suffix)
 */
public class EndsWithFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object suffixVal = arguments.get(1);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("endsWith() requires a string as first argument");
        }
        if (!(suffixVal instanceof String suffix)) {
            throw new RuntimeException("endsWith() requires a string as second argument");
        }

        return str.endsWith(suffix);
    }

    @Override
    public String name() {
        return "endsWith";
    }

    @Override
    public int arity() {
        return 2;
    }
}
