package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that returns the index of a value in an array, or -1 if not found.
 * Usage: arrayIndexOf(array, value)
 */
public class ArrayIndexOfFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object arrVal = arguments.get(0);
        Object searchVal = arguments.get(1);

        if (!(arrVal instanceof List<?> list)) {
            throw new RuntimeException("arrayIndexOf() requires an array as first argument");
        }

        return list.indexOf(searchVal);
    }

    @Override
    public String name() {
        return "arrayIndexOf";
    }

    @Override
    public int arity() {
        return 2;
    }
}
