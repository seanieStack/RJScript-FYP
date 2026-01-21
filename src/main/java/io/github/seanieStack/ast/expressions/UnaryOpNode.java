package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Unary operation expression applying an operator to a single operand.
 *
 * @param operator the unary operator to apply
 * @param operand the expression to apply the operator to
 */
public record UnaryOpNode(
        Operator operator,
        ASTNode operand
) implements ASTNode {

    public enum Operator {
        NEGATE
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
