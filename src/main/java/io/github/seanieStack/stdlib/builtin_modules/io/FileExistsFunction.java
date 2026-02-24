package io.github.seanieStack.stdlib.builtin_modules.io;

import io.github.seanieStack.stdlib.NativeFunction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Native function that checks if a file exists.
 * Usage: fileExists(path)
 */
public class FileExistsFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String path)) {
            throw new RuntimeException("fileExists() requires a string path argument");
        }

        return Files.exists(Path.of(path));
    }

    @Override
    public String name() {
        return "fileExists";
    }

    @Override
    public int arity() {
        return 1;
    }
}
