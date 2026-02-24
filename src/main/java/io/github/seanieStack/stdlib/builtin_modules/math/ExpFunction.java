package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns e raised to the power of a number.
 * Usage: exp(number)
 */
public class ExpFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "exp()");
        return Math.exp(num);
    }

    @Override
    public String name() {
        return "exp";
    }

    @Override
    public int arity() {
        return 1;
    }
}
