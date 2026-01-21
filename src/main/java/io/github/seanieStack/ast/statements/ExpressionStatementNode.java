package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Expression statement that evaluates an expression for its side effects.
 * Used when an expression appears as a standalone statement (e.g., function calls).
 *
 * @param expression the expression to evaluate
 */
public record ExpressionStatementNode(ASTNode expression) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}