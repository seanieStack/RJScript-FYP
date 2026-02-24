package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that checks if a string starts with a prefix.
 * Usage: startsWith(str, prefix)
 */
public class StartsWithFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object prefixVal = arguments.get(1);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("startsWith() requires a string as first argument");
        }
        if (!(prefixVal instanceof String prefix)) {
            throw new RuntimeException("startsWith() requires a string as second argument");
        }

        return str.startsWith(prefix);
    }

    @Override
    public String name() {
        return "startsWith";
    }

    @Override
    public int arity() {
        return 2;
    }
}
