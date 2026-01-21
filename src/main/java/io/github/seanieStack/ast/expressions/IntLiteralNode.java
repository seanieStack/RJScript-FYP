package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Integer literal expression.
 *
 * @param value the integer value
 */
public record IntLiteralNode(int value) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
