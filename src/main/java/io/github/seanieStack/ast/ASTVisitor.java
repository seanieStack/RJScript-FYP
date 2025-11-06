package io.github.seanieStack.ast;

public interface ASTVisitor<T> {
    T visit(ProgramNode node);
    T visit(VarDeclarationNode node);
    T visit(PrintStatementNode node);
    T visit(IfStatementNode node);
    T visit(BlockNode node);
    T visit(ExpressionStatementNode node);

    // Expressions
    T visit(BinaryOpNode node);
    T visit(UnaryOpNode node);
    T visit(IntLiteralNode node);
    T visit(BoolLiteralNode node);
    T visit(VariableNode node);
}