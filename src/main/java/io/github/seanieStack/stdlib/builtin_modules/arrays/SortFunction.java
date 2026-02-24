package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Native function that returns a sorted copy of an array.
 * Usage: sort(array)
 */
public class SortFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof List<?> list)) {
            throw new RuntimeException("sort() requires an array argument");
        }

        ArrayList<Object> result = new ArrayList<>(list);
        result.sort(Comparator.comparing(Object::toString));
        return result;
    }

    @Override
    public String name() {
        return "sort";
    }

    @Override
    public int arity() {
        return 1;
    }
}
