package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native function that returns the current second.
 * Usage: second()
 */
public class SecondFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return LocalDateTime.now().getSecond();
    }

    @Override
    public String name() {
        return "second";
    }

    @Override
    public int arity() {
        return 0;
    }
}
