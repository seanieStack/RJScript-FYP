package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that rounds a number to the nearest integer.
 * Usage: round(number)
 */
public class RoundFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        TypeUtils.requireNumeric(value, "round()");

        if (value instanceof Integer i) {
            return i;
        }
        return (int) Math.round((Double) value);
    }

    @Override
    public String name() {
        return "round";
    }

    @Override
    public int arity() {
        return 1;
    }
}
