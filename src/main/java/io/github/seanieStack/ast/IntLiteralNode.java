package io.github.seanieStack.ast;

public final class IntLiteralNode implements ASTNode {
    private final int value;

    public IntLiteralNode(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
