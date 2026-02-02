package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the square root of a number.
 * Usage: sqrt(number)
 */
public class SqrtFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "sqrt()");

        if (num < 0) {
            throw new RuntimeException("sqrt() cannot compute square root of negative number: " + num);
        }

        return Math.sqrt(num);
    }

    @Override
    public String name() {
        return "sqrt";
    }

    @Override
    public int arity() {
        return 1;
    }
}
