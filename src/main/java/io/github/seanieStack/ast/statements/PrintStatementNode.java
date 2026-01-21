package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Print statement that outputs the result of an expression to stdout.
 *
 * @param expression the expression to evaluate and print
 */
public record PrintStatementNode(ASTNode expression) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}