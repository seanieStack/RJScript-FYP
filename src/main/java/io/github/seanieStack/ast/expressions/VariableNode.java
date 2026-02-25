package io.github.seanieStack.ast.expressions;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

/**
 * Variable reference expression that reads the value of a named variable.
 *
 * @param name the name of the variable to reference
 * @param line the source line number
 * @param column the source column number
 */
public record VariableNode(String name, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
