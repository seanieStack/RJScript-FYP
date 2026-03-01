package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Return statement that exits a function and optionally returns a value.
 *
 * @param expression the expression to evaluate and return (may be null for void return)
 * @param line the source line number
 * @param column the source column number
 */
public record ReturnStatementNode(ASTNode expression, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}