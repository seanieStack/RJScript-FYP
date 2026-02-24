package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that pauses execution for a given number of milliseconds.
 * Usage: sleep(milliseconds)
 */
public class SleepFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof Long) && !(value instanceof Integer)) {
            throw new RuntimeException("sleep() requires an integer argument");
        }

        long ms = ((Number) value).longValue();

        if (ms < 0) {
            throw new RuntimeException("sleep() requires a non-negative value");
        }

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("sleep() was interrupted");
        }

        return null;
    }

    @Override
    public String name() {
        return "sleep";
    }

    @Override
    public int arity() {
        return 1;
    }
}
