package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the sine of an angle in radians.
 * Usage: sine(radians)
 */
public class SineFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "sine()");
        return Math.sin(num);
    }

    @Override
    public String name() {
        return "sine";
    }

    @Override
    public int arity() {
        return 1;
    }
}
