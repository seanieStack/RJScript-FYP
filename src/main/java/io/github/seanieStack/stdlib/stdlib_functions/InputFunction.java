package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Native function that reads a line from standard input.
 * Usage: input()
 * Returns the line read as a string.
 */
public class InputFunction implements NativeFunction {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public Object call(List<Object> arguments) {
        try {
            String line = reader.readLine();
            return line != null ? line : "";
        } catch (IOException e) {
            throw new RuntimeException("Error reading input: " + e.getMessage());
        }
    }

    @Override
    public String name() {
        return "input";
    }

    @Override
    public int arity() {
        return 0;
    }
}
