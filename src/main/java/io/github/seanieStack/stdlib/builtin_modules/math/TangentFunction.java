package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the tangent of an angle in radians.
 * Usage: tangent(radians)
 */
public class TangentFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "tangent()");
        return Math.tan(num);
    }

    @Override
    public String name() {
        return "tangent";
    }

    @Override
    public int arity() {
        return 1;
    }
}
