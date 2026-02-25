package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Represents an indexed assignment statement that modifies an array element.
 * Supports nested array assignment.
 * Example: arr[0] = 10; or matrix[0][1] = 5;
 *
 * @param identifier the name of the array variable
 * @param indices the list of index expressions (one per dimension)
 * @param value the expression to assign to the indexed position
 * @param line the source line number
 * @param column the source column number
 */
public record IndexedAssignmentNode(
        String identifier,
        List<ASTNode> indices,
        ASTNode value,
        int line,
        int column
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
