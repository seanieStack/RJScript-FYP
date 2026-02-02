package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the smallest integer greater than or equal to a number.
 * Usage: ceil(number)
 */
public class CeilFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        TypeUtils.requireNumeric(value, "ceil()");

        if (value instanceof Integer i) {
            return i;
        }
        return (int) Math.ceil((Double) value);
    }

    @Override
    public String name() {
        return "ceil";
    }

    @Override
    public int arity() {
        return 1;
    }
}
