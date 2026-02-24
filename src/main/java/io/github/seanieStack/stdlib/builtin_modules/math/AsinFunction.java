package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the arc sine of a number in radians.
 * Usage: asin(number) where -1 <= number <= 1
 */
public class AsinFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "asin()");
        if (num < -1 || num > 1) {
            throw new RuntimeException("asin() requires a value between -1 and 1, got " + num);
        }
        return Math.asin(num);
    }

    @Override
    public String name() {
        return "asin";
    }

    @Override
    public int arity() {
        return 1;
    }
}
