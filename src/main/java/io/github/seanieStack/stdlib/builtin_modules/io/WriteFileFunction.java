package io.github.seanieStack.stdlib.builtin_modules.io;

import io.github.seanieStack.stdlib.NativeFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Native function that writes content to a file.
 * Usage: writeFile(path, content)
 */
public class WriteFileFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object pathVal = arguments.get(0);
        Object contentVal = arguments.get(1);

        if (!(pathVal instanceof String path)) {
            throw new RuntimeException("writeFile() requires a string path as first argument");
        }
        if (!(contentVal instanceof String content)) {
            throw new RuntimeException("writeFile() requires a string content as second argument");
        }

        try {
            Files.writeString(Path.of(path), content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("writeFile() failed to write file: " + e.getMessage());
        }
    }

    @Override
    public String name() {
        return "writeFile";
    }

    @Override
    public int arity() {
        return 2;
    }
}
