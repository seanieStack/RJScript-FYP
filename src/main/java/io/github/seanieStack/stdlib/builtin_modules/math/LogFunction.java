package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the natural logarithm of a number.
 * Usage: log(number)
 */
public class LogFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "log()");
        if (num <= 0) {
            throw new RuntimeException("log() requires a positive number, got " + num);
        }
        return Math.log(num);
    }

    @Override
    public String name() {
        return "log";
    }

    @Override
    public int arity() {
        return 1;
    }
}
