package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Native function that generates a range of integers.
 * Usage:
 *   range(end) - returns list from 0 to end-1
 *   range(start, end) - returns list from start to end-1
 *   range(start, end, step) - returns list from start to end-1 with step
 * Note: Returns a List that can be used with future array/iteration support.
 */
public class RangeFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        int start, end, step;

        if (arguments.size() == 1) {
            start = 0;
            end = TypeUtils.toInt(arguments.getFirst(), "range()");
            step = 1;
        } else if (arguments.size() == 2) {
            start = TypeUtils.toInt(arguments.get(0), "range()");
            end = TypeUtils.toInt(arguments.get(1), "range()");
            step = 1;
        } else {
            start = TypeUtils.toInt(arguments.get(0), "range()");
            end = TypeUtils.toInt(arguments.get(1), "range()");
            step = TypeUtils.toInt(arguments.get(2), "range()");
        }

        if (step == 0) {
            throw new RuntimeException("range() step cannot be zero");
        }

        List<Integer> result = new ArrayList<>();

        if (step > 0) {
            for (int i = start; i < end; i += step) {
                result.add(i);
            }
        } else {
            for (int i = start; i > end; i += step) {
                result.add(i);
            }
        }

        return result;
    }

    @Override
    public String name() {
        return "range";
    }

    @Override
    public int arity() {
        return -1;
    }
}
