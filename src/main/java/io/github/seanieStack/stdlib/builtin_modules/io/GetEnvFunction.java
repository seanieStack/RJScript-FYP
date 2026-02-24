package io.github.seanieStack.stdlib.builtin_modules.io;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that gets an environment variable.
 * Usage: getEnv(name)
 */
public class GetEnvFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String name)) {
            throw new RuntimeException("getEnv() requires a string name argument");
        }

        String envValue = System.getenv(name);
        return envValue != null ? envValue : "";
    }

    @Override
    public String name() {
        return "getEnv";
    }

    @Override
    public int arity() {
        return 1;
    }
}
