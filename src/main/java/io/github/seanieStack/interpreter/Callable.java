package io.github.seanieStack.interpreter;

/**
 * Common interface for all callable entities in RJScript.
 * Implemented by both user-defined functions and native functions.
 */
public interface Callable {
    /**
     * Returns the number of arguments this callable expects.
     *
     * @return the number of expected arguments, or -1 for variadic functions
     */
    int arity();
}
