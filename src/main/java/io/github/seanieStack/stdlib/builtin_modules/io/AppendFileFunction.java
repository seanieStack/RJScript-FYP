package io.github.seanieStack.stdlib.builtin_modules.io;

import io.github.seanieStack.stdlib.NativeFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Native function that appends content to a file.
 * Usage: appendFile(path, content)
 */
public class AppendFileFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object pathVal = arguments.get(0);
        Object contentVal = arguments.get(1);

        if (!(pathVal instanceof String path)) {
            throw new RuntimeException("appendFile() requires a string path as first argument");
        }
        if (!(contentVal instanceof String content)) {
            throw new RuntimeException("appendFile() requires a string content as second argument");
        }

        try {
            Files.writeString(Path.of(path), content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("appendFile() failed to append to file: " + e.getMessage());
        }
    }

    @Override
    public String name() {
        return "appendFile";
    }

    @Override
    public int arity() {
        return 2;
    }
}
