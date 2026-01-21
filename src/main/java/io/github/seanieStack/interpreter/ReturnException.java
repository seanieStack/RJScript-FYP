package io.github.seanieStack.interpreter;

/**
 * Exception used for control flow to implement early return from functions.
 * Thrown when a return statement is executed and caught by the function call handler.
 */
public class ReturnException extends RuntimeException {
    private final Object value;

    /**
     * Creates a new return exception with the specified return value.
     *
     * @param value the value to return from the function
     */
    public ReturnException(Object value) {
        this.value = value;
    }

    /**
     * Returns the value being returned from the function.
     *
     * @return the return value
     */
    public Object value() {
        return value;
    }
}