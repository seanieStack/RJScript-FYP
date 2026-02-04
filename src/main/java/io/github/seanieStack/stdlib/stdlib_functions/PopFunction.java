package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that removes and returns the last element of an array.
 * Usage: pop(array)
 * Returns the removed element.
 * Throws an error if the array is empty.
 */
public class PopFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object arrayArg = arguments.getFirst();

        TypeUtils.requireArray(arrayArg, "pop()");

        @SuppressWarnings("unchecked") // checked in requiresArray
        List<Object> array = (List<Object>) arrayArg;

        if (array.isEmpty()) {
            throw new RuntimeException("pop() cannot be called on an empty array");
        }

        return array.removeLast();
    }

    @Override
    public String name() {
        return "pop";
    }

    @Override
    public int arity() {
        return 1;
    }
}
