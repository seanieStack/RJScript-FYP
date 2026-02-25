package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Integer literal expression.
 *
 * @param value the integer value
 * @param line the source line number
 * @param column the source column number
 */
public record IntLiteralNode(int value, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
