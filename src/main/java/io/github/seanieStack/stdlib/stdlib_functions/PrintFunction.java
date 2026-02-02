package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

/**
 * Native function that outputs a value to stdout.
 * Usage: print(value)
 * Returns the value that was printed.
 */
public class PrintFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        System.out.println(value);
        return value;
    }

    @Override
    public String name() {
        return "print";
    }

    @Override
    public int arity() {
        return 1;
    }
}
