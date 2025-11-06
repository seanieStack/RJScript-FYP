package io.github.seanieStack.ast;

public final class UnaryOpNode implements ASTNode {
    private final Operator operator;
    private final ASTNode operand;

    public UnaryOpNode(Operator operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public enum Operator {
        NEGATE
    }

    public Operator operator() {
        return operator;
    }

    public ASTNode operand() {
        return operand;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
