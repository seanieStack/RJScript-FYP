package io.github.seanieStack.ast.structural;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Function declaration with named parameters and a body block.
 *
 * @param name the name of the function
 * @param parameters the list of parameter names
 * @param body the block containing the function's statements
 */
public record FunctionDeclarationNode(
        String name,
        List<String> parameters,
        BlockNode body
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}