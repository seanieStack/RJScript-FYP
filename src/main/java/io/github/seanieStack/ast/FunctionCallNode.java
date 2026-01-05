package io.github.seanieStack.ast;

import java.util.List;

public final class FunctionCallNode implements ASTNode {
    private final String name;
    private final List<ASTNode> arguments;

    public FunctionCallNode(String name, List<ASTNode> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String name() {
        return name;
    }

    public List<ASTNode> arguments() {
        return arguments;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}