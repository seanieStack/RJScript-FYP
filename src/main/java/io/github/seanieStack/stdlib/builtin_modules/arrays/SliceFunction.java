package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Native function that returns a slice of an array.
 * Usage: slice(array, start, end)
 */
public class SliceFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object arrVal = arguments.get(0);
        Object startVal = arguments.get(1);
        Object endVal = arguments.get(2);

        if (!(arrVal instanceof List<?> list)) {
            throw new RuntimeException("slice() requires an array as first argument");
        }
        if (!(startVal instanceof Long) && !(startVal instanceof Integer)) {
            throw new RuntimeException("slice() requires an integer as second argument");
        }
        if (!(endVal instanceof Long) && !(endVal instanceof Integer)) {
            throw new RuntimeException("slice() requires an integer as third argument");
        }

        int start = ((Number) startVal).intValue();
        int end = ((Number) endVal).intValue();

        if (start < 0 || end > list.size() || start > end) {
            throw new RuntimeException("slice() indices out of bounds: start=" + start + ", end=" + end + ", length=" + list.size());
        }

        return new ArrayList<>(list.subList(start, end));
    }

    @Override
    public String name() {
        return "slice";
    }

    @Override
    public int arity() {
        return 3;
    }
}
