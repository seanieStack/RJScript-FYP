package io.github.seanieStack.stdlib.builtin_modules.io;

import io.github.seanieStack.stdlib.NativeFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Native function that deletes a file.
 * Usage: deleteFile(path)
 */
public class DeleteFileFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object value = arguments.getFirst();
        if (!(value instanceof String path)) {
            throw new RuntimeException("deleteFile() requires a string path argument");
        }

        try {
            return Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("deleteFile() failed to delete file: " + e.getMessage());
        }
    }

    @Override
    public String name() {
        return "deleteFile";
    }

    @Override
    public int arity() {
        return 1;
    }
}
