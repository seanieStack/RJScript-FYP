package io.github.seanieStack.errors;

public enum ErrorType {
    RUNTIME("RuntimeError"),
    SYNTAX("SyntaxError"),
    IMPORT("ImportError");

    private final String label;

    ErrorType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
