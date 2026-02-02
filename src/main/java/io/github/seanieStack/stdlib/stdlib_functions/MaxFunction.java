package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the maximum of two numbers.
 * Usage: max(a, b)
 */
public class MaxFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object a = arguments.get(0);
        Object b = arguments.get(1);

        double aVal = TypeUtils.toDouble(a, "max()");
        double bVal = TypeUtils.toDouble(b, "max()");
        double result = Math.max(aVal, bVal);

        if (a instanceof Integer && b instanceof Integer) {
            return (int) result;
        }
        return result;
    }

    @Override
    public String name() {
        return "max";
    }

    @Override
    public int arity() {
        return 2;
    }
}
