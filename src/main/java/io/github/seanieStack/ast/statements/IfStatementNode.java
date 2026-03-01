package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.structural.BlockNode;
import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Conditional statement with if/else-if/else branches.
 * Evaluates conditions sequentially and executes the first matching branch.
 *
 * @param condition the primary if condition
 * @param thenBlock the block to execute if the primary condition is true
 * @param elseIfClauses optional list of else-if clauses to check if the primary condition is false
 * @param elseBlock optional else block to execute if no conditions match (may be null)
 * @param line the source line number
 * @param column the source column number
 */
public record IfStatementNode(
        ASTNode condition,
        BlockNode thenBlock,
        List<ElseIfClause> elseIfClauses,
        BlockNode elseBlock,
        int line,
        int column
) implements ASTNode {

    /**
     * Else-if clause pairing a condition with its corresponding block.
     *
     * @param condition the condition to evaluate for this else-if branch
     * @param block the block to execute if this condition is true
     */
    public record ElseIfClause(ASTNode condition, BlockNode block) {
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}