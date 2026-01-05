package io.github.seanieStack.ast;

public final class ForStatementNode implements ASTNode {
    private final ASTNode initialization;
    private final ASTNode condition;
    private final ASTNode update;
    private final BlockNode body;

    public ForStatementNode(ASTNode initialization, ASTNode condition, ASTNode update, BlockNode body) {
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.body = body;
    }

    public ASTNode initialization() {
        return initialization;
    }

    public ASTNode condition() {
        return condition;
    }

    public ASTNode update() {
        return update;
    }

    public BlockNode body() {
        return body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}