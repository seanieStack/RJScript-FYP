package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.structural.BlockNode;
import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * For loop statement with initialization, condition, update, and body.
 * Executes in the pattern: initialize once, then repeatedly check condition, execute body, and update.
 *
 * @param initialization the statement executed once before the loop starts (may be null)
 * @param condition the loop condition evaluated before each iteration (may be null for infinite loop)
 * @param update the statement executed after each iteration (may be null)
 * @param body the block to execute on each iteration
 * @param line the source line number
 * @param column the source column number
 */
public record ForStatementNode(
        ASTNode initialization,
        ASTNode condition,
        ASTNode update,
        BlockNode body,
        int line,
        int column
) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
