package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the minimum of two numbers.
 * Usage: min(a, b)
 */
public class MinFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object a = arguments.get(0);
        Object b = arguments.get(1);

        double aVal = TypeUtils.toDouble(a, "min()");
        double bVal = TypeUtils.toDouble(b, "min()");
        double result = Math.min(aVal, bVal);

        if (a instanceof Integer && b instanceof Integer) {
            return (int) result;
        }
        return result;
    }

    @Override
    public String name() {
        return "min";
    }

    @Override
    public int arity() {
        return 2;
    }
}
