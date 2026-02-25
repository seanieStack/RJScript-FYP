package io.github.seanieStack.ast.statements;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Expression statement that evaluates an expression for its side effects.
 * Used when an expression appears as a standalone statement (e.g., function calls).
 *
 * @param expression the expression to evaluate
 * @param line the source line number
 * @param column the source column number
 */
public record ExpressionStatementNode(ASTNode expression, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
