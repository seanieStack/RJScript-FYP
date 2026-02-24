package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that returns the first element of an array.
 * Usage: first(array)
 */
public class FirstFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof List<?> list)) {
            throw new RuntimeException("first() requires an array argument");
        }

        if (list.isEmpty()) {
            throw new RuntimeException("first() cannot get first element of empty array");
        }

        return list.getFirst();
    }

    @Override
    public String name() {
        return "first";
    }

    @Override
    public int arity() {
        return 1;
    }
}
