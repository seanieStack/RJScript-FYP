package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that returns the last element of an array.
 * Usage: last(array)
 */
public class LastFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof List<?> list)) {
            throw new RuntimeException("last() requires an array argument");
        }

        if (list.isEmpty()) {
            throw new RuntimeException("last() cannot get last element of empty array");
        }

        return list.getLast();
    }

    @Override
    public String name() {
        return "last";
    }

    @Override
    public int arity() {
        return 1;
    }
}
