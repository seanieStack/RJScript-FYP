package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Represents an array literal expression.
 * Example: [1, 2, 3] or []
 *
 * @param elements the list of expressions that make up the array elements
 * @param line the source line number
 * @param column the source column number
 */
public record ArrayLiteralNode(List<ASTNode> elements, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
