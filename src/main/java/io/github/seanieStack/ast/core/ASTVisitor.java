package io.github.seanieStack.ast.core;

import io.github.seanieStack.ast.expressions.*;
import io.github.seanieStack.ast.statements.*;
import io.github.seanieStack.ast.statements.IndexedAssignmentNode;
import io.github.seanieStack.ast.structural.BlockNode;
import io.github.seanieStack.ast.structural.FunctionDeclarationNode;
import io.github.seanieStack.ast.structural.ProgramNode;

/**
 * Visitor interface for traversing and operating on AST nodes.
 * Implements the Visitor pattern to separate tree traversal logic from node operations.
 *
 * @param <T> the return type of visit operations
 */
public interface ASTVisitor<T> {
    T visit(VarDeclarationNode node);
    T visit(VarAssignmentNode node);
    T visit(IfStatementNode node);
    T visit(WhileStatementNode node);
    T visit(ForStatementNode node);
    T visit(ReturnStatementNode node);
    T visit(ExpressionStatementNode node);

    T visit(BinaryOpNode node);
    T visit(FunctionCallNode node);
    T visit(UnaryOpNode node);
    T visit(IntLiteralNode node);
    T visit(BoolLiteralNode node);
    T visit(VariableNode node);
    T visit(FloatLiteralNode floatLiteralNode);
    T visit(StringLiteralNode stringLiteralNode);
    T visit(ArrayLiteralNode arrayLiteralNode);
    T visit(IndexAccessNode indexAccessNode);

    T visit(IndexedAssignmentNode indexedAssignmentNode);

    T visit(BlockNode node);
    T visit(FunctionDeclarationNode node);
    T visit(ProgramNode node);
}