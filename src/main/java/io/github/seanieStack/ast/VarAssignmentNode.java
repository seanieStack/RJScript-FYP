package io.github.seanieStack.ast;

public final class VarAssignmentNode implements  ASTNode {
    private final String identifier;
    private final ASTNode expression;

    public VarAssignmentNode(String identifier, ASTNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String identifier() {
        return identifier;
    }

    public ASTNode expression() {
        return expression;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
