package io.github.seanieStack.stdlib.builtin_modules.strings;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Native function that splits a string by a delimiter into an array.
 * Usage: split(str, delimiter)
 */
public class SplitFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object strVal = arguments.get(0);
        Object delimVal = arguments.get(1);

        if (!(strVal instanceof String str)) {
            throw new RuntimeException("split() requires a string as first argument");
        }
        if (!(delimVal instanceof String delimiter)) {
            throw new RuntimeException("split() requires a string as second argument");
        }

        String[] parts = str.split(java.util.regex.Pattern.quote(delimiter));
        return new ArrayList<>(Arrays.asList(parts));
    }

    @Override
    public String name() {
        return "split";
    }

    @Override
    public int arity() {
        return 2;
    }
}
