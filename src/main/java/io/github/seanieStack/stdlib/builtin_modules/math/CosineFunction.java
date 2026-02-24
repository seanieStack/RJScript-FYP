package io.github.seanieStack.stdlib.builtin_modules.math;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the cosine of an angle in radians.
 * Usage: cosine(radians)
 */
public class CosineFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        double num = TypeUtils.toDouble(value, "cosine()");
        return Math.cos(num);
    }

    @Override
    public String name() {
        return "cosine";
    }

    @Override
    public int arity() {
        return 1;
    }
}
