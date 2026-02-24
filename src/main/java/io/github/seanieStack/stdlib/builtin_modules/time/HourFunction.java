package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native function that returns the current hour (0-23).
 * Usage: hour()
 */
public class HourFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return LocalDateTime.now().getHour();
    }

    @Override
    public String name() {
        return "hour";
    }

    @Override
    public int arity() {
        return 0;
    }
}
