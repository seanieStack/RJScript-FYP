package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Represents an index access expression for arrays.
 * Supports chained indexing for nested arrays.
 * Example: arr[0] or matrix[0][1]
 *
 * @param identifier the name of the array variable
 * @param indices the list of index expressions (one per dimension)
 * @param line the source line number
 * @param column the source column number
 */
public record IndexAccessNode(String identifier, List<ASTNode> indices, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
