package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that checks if an array is empty.
 * Usage: isEmpty(array)
 */
public class IsEmptyFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof List<?> list)) {
            throw new RuntimeException("isEmpty() requires an array argument");
        }

        return list.isEmpty();
    }

    @Override
    public String name() {
        return "isEmpty";
    }

    @Override
    public int arity() {
        return 1;
    }
}
