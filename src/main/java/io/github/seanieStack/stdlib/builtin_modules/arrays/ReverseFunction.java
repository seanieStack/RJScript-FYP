package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Native function that returns a reversed copy of an array.
 * Usage: reverse(array)
 */
public class ReverseFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof List<?> list)) {
            throw new RuntimeException("reverse() requires an array argument");
        }

        ArrayList<Object> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }

    @Override
    public String name() {
        return "reverse";
    }

    @Override
    public int arity() {
        return 1;
    }
}
