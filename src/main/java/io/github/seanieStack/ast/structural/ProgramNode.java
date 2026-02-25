package io.github.seanieStack.ast.structural;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;

import java.util.List;

/**
 * Root node of the abstract syntax tree representing a complete RJScript program.
 *
 * @param statements the list of top-level statements and declarations in the program
 * @param line the source line number
 * @param column the source column number
 */
public record ProgramNode(List<ASTNode> statements, int line, int column) implements ASTNode {

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}