package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;
import java.util.Random;

/**
 * Native function that returns a random float between 0.0 (inclusive) and 1.0 (exclusive).
 * Usage: random()
 */
public class RandomFunction implements NativeFunction {
    private static final Random random = new Random();

    @Override
    public Object call(List<Object> arguments) {
        return random.nextDouble();
    }

    @Override
    public String name() {
        return "random";
    }

    @Override
    public int arity() {
        return 0;
    }
}
