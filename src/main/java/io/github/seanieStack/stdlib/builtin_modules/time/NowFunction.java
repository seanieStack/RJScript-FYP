package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that returns the current time in milliseconds since epoch.
 * Usage: now()
 */
public class NowFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return (double) System.currentTimeMillis();
    }

    @Override
    public String name() {
        return "now";
    }

    @Override
    public int arity() {
        return 0;
    }
}
