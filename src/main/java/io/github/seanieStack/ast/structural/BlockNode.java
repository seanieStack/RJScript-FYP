package io.github.seanieStack.ast.structural;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Block of statements that creates a new lexical scope.
 * Used for function bodies, loop bodies, and conditional branches.
 *
 * @param statements the list of statements within this block
 * @param line the source line number
 * @param column the source column number
 */
public record BlockNode(List<ASTNode> statements, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
