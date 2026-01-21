package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Boolean literal expression.
 *
 * @param value the boolean value (true or false)
 */
public record BoolLiteralNode(boolean value) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
