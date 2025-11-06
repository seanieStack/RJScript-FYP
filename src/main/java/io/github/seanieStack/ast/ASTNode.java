package io.github.seanieStack.ast;

public interface ASTNode {
    <T> T accept(ASTVisitor<T> visitor);
}