package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that replaces all occurrences of a substring.
 * Usage: replace(str, old, new)
 */
public class ReplaceFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object oldVal = arguments.get(1);
        Object newVal = arguments.get(2);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("replace() requires a string as first argument");
        }
        if (!(oldVal instanceof String oldStr)) {
            throw new RuntimeException("replace() requires a string as second argument");
        }
        if (!(newVal instanceof String newStr)) {
            throw new RuntimeException("replace() requires a string as third argument");
        }

        return str.replace(oldStr, newStr);
    }

    @Override
    public String name() {
        return "replace";
    }

    @Override
    public int arity() {
        return 3;
    }
}
