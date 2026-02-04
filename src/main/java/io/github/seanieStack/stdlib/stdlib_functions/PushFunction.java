package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that adds an element to the end of an array.
 * Usage: push(array, value)
 * Returns the new length of the array.
 */
public class PushFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object arrayArg = arguments.get(0);
        Object value = arguments.get(1);

        TypeUtils.requireArray(arrayArg, "push()");

        @SuppressWarnings("unchecked") // checked in requiresArray
        List<Object> array = (List<Object>) arrayArg;
        array.add(value);

        return array.size();
    }

    @Override
    public String name() {
        return "push";
    }

    @Override
    public int arity() {
        return 2;
    }
}
