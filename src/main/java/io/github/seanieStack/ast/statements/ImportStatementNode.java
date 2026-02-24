package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Import statement that imports a function from a builtin module.
 *
 * @param functionName the name of the function to import
 * @param moduleName the name of the module to import from
 */
public record ImportStatementNode(
        String functionName,
        String moduleName
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
