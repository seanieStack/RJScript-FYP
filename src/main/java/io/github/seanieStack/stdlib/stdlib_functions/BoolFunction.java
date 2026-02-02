package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that converts a value to a boolean.
 * Usage: bool(value)
 * - Numbers: 0 is false, non-zero is true
 * - Strings: empty string is false, non-empty is true
 * - null: false
 */
public class BoolFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();

        return switch (value) {
            case null -> false;
            case Boolean b -> value;
            case Integer i -> i != 0;
            case Double d -> d != 0.0;
            case String s -> !s.isEmpty();
            default -> true;
        };

    }

    @Override
    public String name() {
        return "bool";
    }

    @Override
    public int arity() {
        return 1;
    }
}
