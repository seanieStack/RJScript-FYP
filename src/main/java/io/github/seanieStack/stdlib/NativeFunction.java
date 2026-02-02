package io.github.seanieStack.stdlib;

import io.github.seanieStack.interpreter.Callable;

import java.util.List;

/**
 * Interface for built-in native functions in RJScript.
 * Native functions are implemented in Java and provide core functionality.
 */
public interface NativeFunction extends Callable {
    /**
     * Executes the native function with the provided arguments.
     *
     * @param arguments the list of evaluated arguments passed to the function
     * @return the result of the function execution
     */
    Object call(List<Object> arguments);

    /**
     * Returns the name of this native function.
     *
     * @return the function name as it appears in RJScript code
     */
    String name();
}
