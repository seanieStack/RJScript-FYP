package io.github.seanieStack.stdlib.builtin_modules.io;

import io.github.seanieStack.stdlib.NativeFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Native function that reads the contents of a file.
 * Usage: readFile(path)
 */
public class ReadFileFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String path)) {
            throw new RuntimeException("readFile() requires a string path argument");
        }

        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("readFile() failed to read file: " + e.getMessage());
        }
    }

    @Override
    public String name() {
        return "readFile";
    }

    @Override
    public int arity() {
        return 1;
    }
}
