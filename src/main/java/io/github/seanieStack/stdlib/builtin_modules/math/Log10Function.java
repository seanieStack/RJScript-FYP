package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the base-10 logarithm of a number.
 * Usage: log10(number)
 */
public class Log10Function implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "log10()");
        if (num <= 0) {
            throw new RuntimeException("log10() requires a positive number, got " + num);
        }
        return Math.log10(num);
    }

    @Override
    public String name() {
        return "log10";
    }

    @Override
    public int arity() {
        return 1;
    }
}
