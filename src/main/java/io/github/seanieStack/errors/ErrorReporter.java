package io.github.seanieStack.errors;

public final class ErrorReporter {

    private ErrorReporter() {}

    public static String format(RJScriptError e, String fileName) {
        String label = switch (e.getType()) {
            case RUNTIME -> "RuntimeError";
            case SYNTAX  -> "SyntaxError";
            case IMPORT  -> "ImportError";
        };
        return String.format("[%s] %s%n  --> %s:%d:%d",
            label, e.getMessage(), fileName, e.getLine(), e.getColumn());
    }

    public static String format(RJScriptError e) {
        return format(e, "<repl>");
    }
}
