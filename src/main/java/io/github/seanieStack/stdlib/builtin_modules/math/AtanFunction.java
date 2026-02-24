package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the arc tangent of a number in radians.
 * Usage: atan(number)
 */
public class AtanFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "atan()");
        return Math.atan(num);
    }

    @Override
    public String name() {
        return "atan";
    }

    @Override
    public int arity() {
        return 1;
    }
}
