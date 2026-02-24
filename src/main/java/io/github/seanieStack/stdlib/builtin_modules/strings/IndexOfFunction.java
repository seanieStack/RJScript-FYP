package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that returns the index of a substring, or -1 if not found.
 * Usage: indexOf(str, search)
 */
public class IndexOfFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object searchVal = arguments.get(1);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("indexOf() requires a string as first argument");
        }
        if (!(searchVal instanceof String search)) {
            throw new RuntimeException("indexOf() requires a string as second argument");
        }

        return str.indexOf(search);
    }

    @Override
    public String name() {
        return "indexOf";
    }

    @Override
    public int arity() {
        return 2;
    }
}
