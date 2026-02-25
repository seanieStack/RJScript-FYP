package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Variable declaration statement that introduces a new variable with an initial value.
 *
 * @param identifier the name of the variable being declared
 * @param expression the initialization expression for the variable
 * @param line the source line number
 * @param column the source column number
 */
public record VarDeclarationNode(
        String identifier,
        ASTNode expression,
        int line,
        int column
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}