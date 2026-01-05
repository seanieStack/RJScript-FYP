package io.github.seanieStack.ast;

public final class WhileStatementNode implements ASTNode {
    private final ASTNode condition;
    private final BlockNode body;

    public WhileStatementNode(ASTNode condition, BlockNode body) {
        this.condition = condition;
        this.body = body;
    }

    public ASTNode condition() {
        return condition;
    }

    public BlockNode body() {
        return body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}