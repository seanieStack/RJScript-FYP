package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.structural.BlockNode;
import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * While loop statement that repeatedly executes a block while a condition is true.
 *
 * @param condition the loop condition evaluated before each iteration
 * @param body the block to execute on each iteration
 * @param line the source line number
 * @param column the source column number
 */
public record WhileStatementNode(
        ASTNode condition,
        BlockNode body,
        int line,
        int column
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}