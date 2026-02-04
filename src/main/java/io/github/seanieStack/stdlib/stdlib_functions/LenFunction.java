package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the length of a string or array.
 * Usage: len(string) or len(array)
 */
public class LenFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (value instanceof String s) {
            return s.length();
        } else if (value instanceof List<?> list) {
            return list.size();
        }
        throw new RuntimeException("len() requires a string or array argument, got " + TypeUtils.getTypeName(value));
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
