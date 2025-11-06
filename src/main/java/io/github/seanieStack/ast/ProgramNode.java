package io.github.seanieStack.ast;

import java.util.List;

public final class ProgramNode implements ASTNode {
    private final List<ASTNode> statements;

    public ProgramNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    public List<ASTNode> statements() {
        return statements;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}