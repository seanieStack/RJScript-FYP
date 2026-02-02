package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;
import java.util.Random;

/**
 * Native function that returns a random integer in a range.
 * Usage: randomInt(min, max) - returns random int from min (inclusive) to max (inclusive)
 */
public class RandomIntFunction implements NativeFunction {
    private static final Random random = new Random();

    @Override
    public Object call(List<Object> arguments) {
        int min = TypeUtils.toInt(arguments.get(0), "randomInt()");
        int max = TypeUtils.toInt(arguments.get(1), "randomInt()");

        if (min > max) {
            throw new RuntimeException("randomInt() min (" + min + ") cannot be greater than max (" + max + ")");
        }

        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public String name() {
        return "randomInt";
    }

    @Override
    public int arity() {
        return 2;
    }
}
