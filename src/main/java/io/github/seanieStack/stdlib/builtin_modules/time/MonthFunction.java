package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native function that returns the current month (1-12).
 * Usage: month()
 */
public class MonthFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return LocalDateTime.now().getMonthValue();
    }

    @Override
    public String name() {
        return "month";
    }

    @Override
    public int arity() {
        return 0;
    }
}
