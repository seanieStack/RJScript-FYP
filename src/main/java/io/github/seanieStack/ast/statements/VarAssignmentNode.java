package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Variable assignment statement that updates an existing variable's value.
 *
 * @param identifier the name of the variable being assigned
 * @param expression the new value expression to assign to the variable
 */
public record VarAssignmentNode(
        String identifier,
        ASTNode expression
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
