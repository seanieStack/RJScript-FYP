package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Return statement that exits a function and optionally returns a value.
 *
 * @param expression the expression to evaluate and return (may be null for void return)
 */
public record ReturnStatementNode(ASTNode expression) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}