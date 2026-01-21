package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Variable declaration statement that introduces a new variable with an initial value.
 *
 * @param identifier the name of the variable being declared
 * @param expression the initialization expression for the variable
 */
public record VarDeclarationNode(
        String identifier,
        ASTNode expression
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}