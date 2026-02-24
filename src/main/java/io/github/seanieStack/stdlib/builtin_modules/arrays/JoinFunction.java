package io.github.seanieStack.stdlib.builtin_modules.arrays;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Native function that joins array elements into a string with a delimiter.
 * Usage: join(array, delimiter)
 */
public class JoinFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        Object arrVal = arguments.get(0);
        Object delimVal = arguments.get(1);

        if (!(arrVal instanceof List<?> list)) {
            throw new RuntimeException("join() requires an array as first argument");
        }
        if (!(delimVal instanceof String delimiter)) {
            throw new RuntimeException("join() requires a string as second argument");
        }

        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public String name() {
        return "join";
    }

    @Override
    public int arity() {
        return 2;
    }
}
