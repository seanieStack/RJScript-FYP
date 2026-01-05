package io.github.seanieStack.ast;

import io.github.seanieStack.parser.RJScriptBaseVisitor;
import io.github.seanieStack.parser.RJScriptParser;
import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends RJScriptBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(RJScriptParser.ProgramContext ctx) {
        return new ProgramNode(ctx.statement().stream().map(this::visit).toList());
    }

    @Override
    public ASTNode visitVarDeclaration(RJScriptParser.VarDeclarationContext ctx) {
        return new VarDeclarationNode(
                ctx.IDENTIFIER().getText(),
                visit(ctx.expression())
        );
    }

    @Override
    public ASTNode visitVarAssignment(RJScriptParser.VarAssignmentContext ctx){
        return new VarAssignmentNode(
                ctx.IDENTIFIER().getText(),
                visit(ctx.expression())
        );
    }

    @Override
    public ASTNode visitPrintStatement(RJScriptParser.PrintStatementContext ctx) {
        return new PrintStatementNode(visit(ctx.expression()));
    }

    @Override
    public ASTNode visitIfStatement(RJScriptParser.IfStatementContext ctx) {
        //if condition
        ASTNode condition = visit(ctx.expression());

        //then block
        BlockNode thenBlock = (BlockNode) visit(ctx.block());

        //possible else if conditions
        List<IfStatementNode.ElseIfClause> elseIfClauses = new ArrayList<>();
        for (RJScriptParser.ElseIfStatementContext elseIfCtx : ctx.elseIfStatement()) {

            //if else condition
            ASTNode elseIfCondition = visit(elseIfCtx.expression());

            //then block
            BlockNode elseIfBlock = (BlockNode) visit(elseIfCtx.block());

            //store
            elseIfClauses.add(new IfStatementNode.ElseIfClause(elseIfCondition, elseIfBlock));
        }

        //possible else block
        BlockNode elseBlock = null;
        if (ctx.elseStatement() != null) {
            elseBlock = (BlockNode) visit(ctx.elseStatement().block());
        }

        return new IfStatementNode(
                condition,
                thenBlock,
                elseIfClauses,
                elseBlock
        );
    }

    @Override
    public ASTNode visitWhileStatement(RJScriptParser.WhileStatementContext ctx) {
        ASTNode condition = visit(ctx.expression());
        BlockNode body = (BlockNode) visit(ctx.block());
        return new WhileStatementNode(condition, body);
    }

    @Override
    public ASTNode visitForStatement(RJScriptParser.ForStatementContext ctx) {
        ASTNode initialization = visit(ctx.forInit());
        ASTNode condition = visit(ctx.expression());
        ASTNode update = visit(ctx.forUpdate());
        BlockNode body = (BlockNode) visit(ctx.block());
        return new ForStatementNode(initialization, condition, update, body);
    }

    @Override
    public ASTNode visitForInit(RJScriptParser.ForInitContext ctx) {
        if (ctx.LET() != null) {
            return new VarDeclarationNode(
                    ctx.IDENTIFIER().getText(),
                    visit(ctx.expression())
            );
        } else {
            return new VarAssignmentNode(
                    ctx.IDENTIFIER().getText(),
                    visit(ctx.expression())
            );
        }
    }

    @Override
    public ASTNode visitForUpdate(RJScriptParser.ForUpdateContext ctx) {
        return new VarAssignmentNode(
                ctx.IDENTIFIER().getText(),
                visit(ctx.expression())
        );
    }

    @Override
    public ASTNode visitFunctionDeclaration(RJScriptParser.FunctionDeclarationContext ctx) {
        String name = ctx.IDENTIFIER().getText();

        List<String> parameters = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (var identifier : ctx.parameterList().IDENTIFIER()) {
                parameters.add(identifier.getText());
            }
        }

        BlockNode body = (BlockNode) visit(ctx.block());

        return new FunctionDeclarationNode(name, parameters, body);
    }

    @Override
    public ASTNode visitReturnStatement(RJScriptParser.ReturnStatementContext ctx) {
        return new ReturnStatementNode(visit(ctx.expression()));
    }

    @Override
    public ASTNode visitBlock(RJScriptParser.BlockContext ctx) {
        return new BlockNode(ctx.statement().stream().map(this::visit).toList());
    }

    @Override
    public ASTNode visitExpressionStatement(RJScriptParser.ExpressionStatementContext ctx) {
        return new ExpressionStatementNode(visit(ctx.expression()));
    }

    @Override
    public ASTNode visitExpression(RJScriptParser.ExpressionContext ctx) {
        return visit(ctx.comparison());
    }

    @Override
    public ASTNode visitComparison(RJScriptParser.ComparisonContext ctx) {
        //could be left side of comparison or just additive
        ASTNode left = visit(ctx.additive(0));

        //check if is comparison
        if (ctx.additive().size() > 1) {
            String operator = ctx.getChild(1).getText();
            ASTNode right = visit(ctx.additive(1));

            BinaryOpNode.Operator op = switch (operator) {
                case "<" -> BinaryOpNode.Operator.LESS_THAN;
                case ">" -> BinaryOpNode.Operator.GREATER_THAN;
                case "<=" -> BinaryOpNode.Operator.LESS_EQUAL;
                case ">=" -> BinaryOpNode.Operator.GREATER_EQUAL;
                case "==" -> BinaryOpNode.Operator.EQUAL;
                case "!=" -> BinaryOpNode.Operator.NOT_EQUAL;
                default -> throw new RuntimeException("Unknown comparison operator: " + operator);
            };

            return new BinaryOpNode(op, left, right);
        }

        //else just return additive
        return left;
    }

    @Override
    public ASTNode visitAdditive(RJScriptParser.AdditiveContext ctx) {
        ASTNode result = visit(ctx.multiplicative(0));

        //loop over all parts
        for (int i = 0; i < ctx.multiplicative().size() - 1; i++) {
            String operator = ctx.getChild(2 * i + 1).getText();
            ASTNode right = visit(ctx.multiplicative(i + 1));

            BinaryOpNode.Operator op = operator.equals("+") ?
                    BinaryOpNode.Operator.ADD :
                    BinaryOpNode.Operator.SUBTRACT;

            result = new BinaryOpNode(op, result, right);
        }

        return result;
    }

    @Override
    public ASTNode visitMultiplicative(RJScriptParser.MultiplicativeContext ctx) {
        ASTNode result = visit(ctx.unary(0));

        //loop over all parts
        for (int i = 0; i < ctx.unary().size() - 1; i++) {
            String operator = ctx.getChild(2 * i + 1).getText();
            ASTNode right = visit(ctx.unary(i + 1));

            BinaryOpNode.Operator op = operator.equals("*") ?
                    BinaryOpNode.Operator.MULTIPLY :
                    BinaryOpNode.Operator.DIVIDE;

            result = new BinaryOpNode(op, result, right);
        }

        return result;
    }

    @Override
    public ASTNode visitUnary(RJScriptParser.UnaryContext ctx) {
        if (ctx.MINUS() != null) {
            ASTNode operand = visit(ctx.unary());
            return new UnaryOpNode(UnaryOpNode.Operator.NEGATE, operand);
        } else {
            return visit(ctx.primary());
        }
    }

    @Override
    public ASTNode visitPrimary(RJScriptParser.PrimaryContext ctx) {
        if (ctx.INT() != null) {
            return new IntLiteralNode(Integer.parseInt(ctx.INT().getText()));
        } else if (ctx.BOOLEAN() != null) {
            return new BoolLiteralNode(Boolean.parseBoolean(ctx.BOOLEAN().getText()));
        } else if (ctx.functionCall() != null) {
            return visit(ctx.functionCall());
        } else if (ctx.IDENTIFIER() != null) {
            return new VariableNode(ctx.IDENTIFIER().getText());
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        throw new RuntimeException("Unknown primary expression");
    }

    @Override
    public ASTNode visitFunctionCall(RJScriptParser.FunctionCallContext ctx) {
        String name = ctx.IDENTIFIER().getText();

        List<ASTNode> arguments = new ArrayList<>();
        if (ctx.argumentList() != null) {
            for (var expr : ctx.argumentList().expression()) {
                arguments.add(visit(expr));
            }
        }

        return new FunctionCallNode(name, arguments);
    }
}
