package io.github.seanieStack.stdlib.builtin_modules.time;

import io.github.seanieStack.stdlib.NativeFunction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Native function that returns the current year.
 * Usage: year()
 */
public class YearFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return LocalDateTime.now().getYear();
    }

    @Override
    public String name() {
        return "year";
    }

    @Override
    public int arity() {
        return 0;
    }
}
