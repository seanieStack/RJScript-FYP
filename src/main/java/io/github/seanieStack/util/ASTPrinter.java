package io.github.seanieStack.util;

import io.github.seanieStack.ast.*;
import java.util.List;

public class ASTPrinter implements ASTVisitor<String> {

    private String formatChild(String childString, boolean isLast) {
        String[] lines = childString.split("\n");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            if (i == 0) {
                sb.append(isLast ? "└── " : "├── ");
            } else {
                sb.append(isLast ? "    " : "│   ");
            }
            sb.append(lines[i]);
            if (i < lines.length - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public String visit(ProgramNode node) {
        StringBuilder sb = new StringBuilder("program");
        List<ASTNode> statements = node.statements();
        for (int i = 0; i < statements.size(); i++) {
            boolean isLast = (i == statements.size() - 1);
            sb.append("\n").append(
                    formatChild(
                            statements.get(i).accept(this),
                            isLast
                    )
            );
        }
        return sb.toString();
    }

    @Override
    public String visit(VarDeclarationNode node) {
        return "varDeclaration\n" +
                "├── identifier: '" + node.identifier() + "'\n" +
                formatChild(
                        node.expression().accept(this),
                        true
                );
    }

    @Override
    public String visit(VarAssignmentNode node) {
        return "varAssignment\n" +
                "├── identifier: '" + node.identifier() + "'\n" +
                formatChild(
                        node.expression().accept(this),
                        true
                );
    }

    @Override
    public String visit(PrintStatementNode node) {
        return "printStatement\n" +
                formatChild(
                        node.expression().accept(this),
                        true
                );
    }

    @Override
    public String visit(WhileStatementNode node) {
        return "whileStatement\n" +
                formatChild(
                        "condition\n" +
                        formatChild(
                                node.condition().accept(this),
                                true
                        ),
                        false
                ) +
                "\n" +
                formatChild(
                        "body\n" +
                        formatChild(
                                node.body().accept(this),
                                true
                        ),
                        true
                );
    }

    @Override
    public String visit(ForStatementNode node) {
        return "forStatement\n" +
                formatChild(
                        "initialization\n" +
                        formatChild(
                                node.initialization().accept(this),
                                true
                        ),
                        false
                ) +
                "\n" +
                formatChild(
                        "condition\n" +
                        formatChild(
                                node.condition().accept(this),
                                true
                        ),
                        false
                ) +
                "\n" +
                formatChild(
                        "update\n" +
                        formatChild(
                                node.update().accept(this),
                                true
                        ),
                        false
                ) +
                "\n" +
                formatChild(
                        "body\n" +
                        formatChild(
                                node.body().accept(this),
                                true
                        ),
                        true
                );
    }

    @Override
    public String visit(IfStatementNode node) {
        StringBuilder sb = new StringBuilder("ifStatement");

        int numOfElseIf = node.elseIfClauses().size();
        int hasElse = node.elseBlock() != null ? 1 : 0;

        int totalParts = 2 + numOfElseIf + hasElse;
        int currentPart = 2;

        //condition
        sb.append("\n").append(
                formatChild(
                        "condition\n" +
                        formatChild(
                                node.condition().accept(this),
                                true
                        ),
                        false
                )
        );

        //then block
        sb.append("\n").append(
                formatChild(
                        "thenBlock\n" +
                        formatChild(
                                node.thenBlock().accept(this),
                                true
                        ),
                        ++currentPart == totalParts
                )
        );

        //else if clauses
        for (IfStatementNode.ElseIfClause elseIf : node.elseIfClauses()) {
            String elseIfStr = "elseIfClause\n";

            elseIfStr += formatChild(
                    "condition\n" +
                            formatChild(
                                    elseIf.condition().accept(this),
                                    true
                            ),
                    false
            );

            elseIfStr += "\n" + formatChild(
                    elseIf.block().accept(this),
                    true
            );

            sb.append("\n").append(
                    formatChild(
                            elseIfStr,
                            ++currentPart == totalParts
                    )
            );
        }

        //else block
        if (node.elseBlock() != null) {
            sb.append("\n").append(
                    formatChild(
                            "elseBlock\n" + formatChild(
                                    node.elseBlock().accept(this),
                                    true
                            ),
                            ++currentPart == totalParts
                    )
            );
        }

        return sb.toString();
    }

    @Override
    public String visit(BlockNode node) {
        StringBuilder sb = new StringBuilder("block");
        List<ASTNode> statements = node.statements();
        for (int i = 0; i < statements.size(); i++) {
            boolean isLast = (i == statements.size() - 1);
            sb.append("\n").append(
                    formatChild(
                            statements.get(i).accept(this),
                            isLast
                    )
            );
        }
        return sb.toString();
    }

    @Override
    public String visit(ExpressionStatementNode node) {
        return "expressionStatement\n" +
                formatChild(
                        node.expression().accept(this),
                        true
                );
    }

    @Override
    public String visit(BinaryOpNode node) {
        return "binaryOp: " + node.operator() + "\n" +
                formatChild(
                        node.left().accept(this),
                        false
                ) +
                "\n" +
                formatChild(
                        node.right().accept(this),
                        true
                );
    }

    @Override
    public String visit(UnaryOpNode node) {
        return "unaryOp: " + node.operator() + "\n" +
                formatChild(
                        node.operand().accept(this),
                        true
                );
    }

    @Override
    public String visit(IntLiteralNode node) {
        return "intLiteral: " + node.value();
    }

    @Override
    public String visit(BoolLiteralNode node) {
        return "boolLiteral: " + node.value();
    }

    @Override
    public String visit(VariableNode node) {
        return "variable: '" + node.name() + "'";
    }
}
