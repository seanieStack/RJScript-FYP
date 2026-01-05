package io.github.seanieStack.ast;

import java.util.List;

public final class FunctionDeclarationNode implements ASTNode {
    private final String name;
    private final List<String> parameters;
    private final BlockNode body;

    public FunctionDeclarationNode(String name, List<String> parameters, BlockNode body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public String name() {
        return name;
    }

    public List<String> parameters() {
        return parameters;
    }

    public BlockNode body() {
        return body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}