package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the absolute value of a number.
 * Usage: abs(number)
 */
public class AbsFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        TypeUtils.requireNumeric(value, "abs()");

        if (value instanceof Integer i) {
            return Math.abs(i);
        } else {
            return Math.abs((Double) value);
        }
    }

    @Override
    public String name() {
        return "abs";
    }

    @Override
    public int arity() {
        return 1;
    }
}
