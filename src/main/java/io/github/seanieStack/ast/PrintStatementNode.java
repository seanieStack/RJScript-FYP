package io.github.seanieStack.ast;

public final class PrintStatementNode implements ASTNode {
    private final ASTNode expression;

    public PrintStatementNode(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode expression() {
        return expression;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}