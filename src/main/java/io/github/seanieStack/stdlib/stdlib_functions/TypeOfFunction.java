package io.github.seanieStack.stdlib.stdlib_functions;

import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.List;

/**
 * Native function that returns the type of value as a string.
 * Usage: typeof(value)
 * Returns "int", "float", "string", "bool", or "null".
 */
public class TypeOfFunction implements NativeFunction {
    @Override
    public Object call(List<Object> arguments) {
        return TypeUtils.getTypeName(arguments.getFirst());
    }

    @Override
    public String name() {
        return "typeof";
    }

    @Override
    public int arity() {
        return 1;
    }
}
