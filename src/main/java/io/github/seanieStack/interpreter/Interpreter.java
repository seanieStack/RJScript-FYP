package io.github.seanieStack.interpreter;

import io.github.seanieStack.parser.RJScriptBaseVisitor;
import io.github.seanieStack.parser.RJScriptParser;

public class Interpreter extends RJScriptBaseVisitor<Integer> {

    @Override
    public Integer visitProgram(RJScriptParser.ProgramContext ctx) {
        Integer result = null;
        for (RJScriptParser.StatementContext statement : ctx.statement()) {
            result = visit(statement);
        }
        return result;
    }

    @Override
    public Integer visitStatement(RJScriptParser.StatementContext ctx) {
        Integer result = visit(ctx.expression());
        System.out.println(result);
        return result;
    }

    @Override
    public Integer visitExpression(RJScriptParser.ExpressionContext ctx) {
        return visit(ctx.additive());
    }

    @Override
    public Integer visitAdditive(RJScriptParser.AdditiveContext ctx) {
        Integer result = visit(ctx.primary(0));

        for (int i = 0; i < ctx.primary().size() - 1; i++) {
            String operator = ctx.getChild(2 * i + 1).getText();
            Integer right = visit(ctx.primary(i + 1));

            if (operator.equals("+")) {
                result = result + right;
            } else if (operator.equals("-")) {
                result = result - right;
            }
        }

        return result;
    }

    @Override
    public Integer visitPrimary(RJScriptParser.PrimaryContext ctx) {
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        return 0;
    }
}
