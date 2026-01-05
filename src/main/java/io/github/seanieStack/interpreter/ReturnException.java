package io.github.seanieStack.interpreter;

public class ReturnException extends RuntimeException {
    private final Object value;

    public ReturnException(Object value) {
        this.value = value;
    }

    public Object value() {
        return value;
    }
}