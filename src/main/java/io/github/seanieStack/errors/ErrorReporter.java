package io.github.seanieStack.errors;

public final class ErrorReporter {

    private ErrorReporter() {}

    public static String format(RJScriptError e, String fileName) {
        return String.format("[%s] %s%n  --> %s:%d:%d",
            e.getType().label(), e.getMessage(), fileName, e.getLine(), e.getColumn());
    }
}
