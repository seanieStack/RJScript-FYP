package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that checks if an array contains a value.
 * Usage: contains(array, value)
 */
public class ContainsFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object arrVal = arguments.get(0);
        Object searchVal = arguments.get(1);

        if (!(arrVal instanceof List<?> list)) {
            throw new RuntimeException("contains() requires an array as first argument");
        }

        return list.contains(searchVal);
    }

    @Override
    public String name() {
        return "contains";
    }

    @Override
    public int arity() {
        return 2;
    }
}
