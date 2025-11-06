package io.github.seanieStack.ast;

public final class BinaryOpNode implements ASTNode {
    private final Operator operator;
    private final ASTNode left;
    private final ASTNode right;

    public BinaryOpNode(Operator operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE,

        LESS_THAN, GREATER_THAN, LESS_EQUAL, GREATER_EQUAL, EQUAL, NOT_EQUAL
    }

    public Operator operator() {
        return operator;
    }

    public ASTNode left() {
        return left;
    }

    public ASTNode right() {
        return right;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
