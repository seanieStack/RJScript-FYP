package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Binary operation expression combining two operands with an operator.
 * Supports arithmetic (+, -, *, /) and comparison (<, >, <=, >=, ==, !=) operations.
 *
 * @param operator the binary operator to apply
 * @param left the left operand expression
 * @param right the right operand expression
 */
public record BinaryOpNode(
        Operator operator,
        ASTNode left,
        ASTNode right
) implements ASTNode {

    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE,
        LESS_THAN, GREATER_THAN, LESS_EQUAL, GREATER_EQUAL, EQUAL, NOT_EQUAL
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
