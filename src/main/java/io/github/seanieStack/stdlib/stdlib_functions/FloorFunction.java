package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the largest integer less than or equal to a number.
 * Usage: floor(number)
 */
public class FloorFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        TypeUtils.requireNumeric(value, "floor()");

        if (value instanceof Integer i) {
            return i;
        }
        return (int) Math.floor((Double) value);
    }

    @Override
    public String name() {
        return "floor";
    }

    @Override
    public int arity() {
        return 1;
    }
}
