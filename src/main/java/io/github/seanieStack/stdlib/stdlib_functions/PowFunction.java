package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns base raised to the power of exponent.
 * Usage: pow(base, exponent)
 */
public class PowFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object base = arguments.get(0);
        Object exp = arguments.get(1);

        double baseVal = TypeUtils.toDouble(base, "pow()");
        double expVal = TypeUtils.toDouble(exp, "pow()");
        double result = Math.pow(baseVal, expVal);

        if (base instanceof Integer && exp instanceof Integer && result == Math.floor(result) && !Double.isInfinite(result)) {
            return (int) result;
        }
        return result;
    }

    @Override
    public String name() {
        return "pow";
    }

    @Override
    public int arity() {
        return 2;
    }
}
