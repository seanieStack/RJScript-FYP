package io.github.seanieStack.errors;

public class RJScriptError extends RuntimeException {
    private final ErrorType type;
    private final int line;
    private final int column;

    public RJScriptError(ErrorType type, String message, int line, int column) {
        super(message);
        this.type   = type;
        this.line   = line;
        this.column = column;
    }

    public ErrorType getType(){
        return type;
    }

    public int getLine(){
        return line;
    }

    public int getColumn(){
        return column;
    }
}
