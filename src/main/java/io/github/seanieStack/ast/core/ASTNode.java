package io.github.seanieStack.ast.core;

/**
 * Base interface for all nodes in the Abstract Syntax Tree (AST).
 * Represents a single unit in the parsed program structure.
 */
public interface ASTNode {
    /**
     * Accepts a visitor to perform operations on this node (Visitor pattern).
     *
     * @param <T> the return type of the visitor operation
     * @param visitor the visitor to accept
     * @return the result of the visitor operation
     */
    <T> T accept(ASTVisitor<T> visitor);

    int line();
    int column();
}