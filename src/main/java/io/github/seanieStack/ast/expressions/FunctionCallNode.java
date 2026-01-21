package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Function call expression that invokes a function with arguments.
 *
 * @param name the name of the function to call
 * @param arguments the list of argument expressions passed to the function
 */
public record FunctionCallNode(
        String name,
        List<ASTNode> arguments
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}