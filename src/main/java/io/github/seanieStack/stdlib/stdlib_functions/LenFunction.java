package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the length of a string.
 * Usage: len(string)
 */
public class LenFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        TypeUtils.requireString(value, "len()");
        return ((String) value).length();
    }

    @Override
    public String name() {
        return "len";
    }

    @Override
    public int arity() {
        return 1;
    }
}
