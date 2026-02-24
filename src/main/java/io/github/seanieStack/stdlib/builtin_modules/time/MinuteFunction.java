package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native function that returns the current minute.
 * Usage: minute()
 */
public class MinuteFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return LocalDateTime.now().getMinute();
    }

    @Override
    public String name() {
        return "minute";
    }

    @Override
    public int arity() {
        return 0;
    }
}
