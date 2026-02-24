package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native function that returns the current day of the month.
 * Usage: day()
 */
public class DayFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return LocalDateTime.now().getDayOfMonth();
    }

    @Override
    public String name() {
        return "day";
    }

    @Override
    public int arity() {
        return 0;
    }
}
