package io.github.seanieStack.interpreter;

import io.github.seanieStack.parser.RJScriptBaseVisitor;
import io.github.seanieStack.parser.RJScriptParser;
import java.util.HashMap;
import java.util.Map;

public class Interpreter extends RJScriptBaseVisitor<Integer> {

    private final Map<String, Integer> variables = new HashMap<>();

    @Override
    public Integer visitProgram(RJScriptParser.ProgramContext ctx) {
        Integer result = null;
        for (RJScriptParser.StatementContext statement : ctx.statement()) {
            result = visit(statement);
        }
        return result;
    }

    @Override
    public Integer visitVarDeclaration(RJScriptParser.VarDeclarationContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        Integer value = visit(ctx.expression());
        variables.put(varName, value);
        return value;
    }

    @Override
    public Integer visitPrintStatement(RJScriptParser.PrintStatementContext ctx) {
        Integer value = visit(ctx.expression());
        System.out.println(value);
        return value;
    }

    @Override
    public Integer visitExpressionStatement(RJScriptParser.ExpressionStatementContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Integer visitExpression(RJScriptParser.ExpressionContext ctx) {
        return visit(ctx.additive());
    }

    @Override
    public Integer visitAdditive(RJScriptParser.AdditiveContext ctx) {
        Integer result = visit(ctx.multiplicative(0));

        for(int i = 0; i < ctx.multiplicative().size() - 1; i++){
            String operator = ctx.getChild(2 * i + 1).getText();
            int right = visit(ctx.multiplicative(i + 1));

            if(operator.equals("+")){
                result = result + right;
            }
            else if(operator.equals("-")){
                result = result - right;
            }
        }
        return result;
    }

    public Integer visitMultiplicative(RJScriptParser.MultiplicativeContext ctx) {
        Integer result = visit(ctx.unary(0));

        for(int i = 0; i < ctx.unary().size() - 1; i++){
            String operator = ctx.getChild(2 * i + 1).getText();
            int right = visit(ctx.unary(i + 1));

            if(operator.equals("*")){
                result = result * right;
            }
            else if(operator.equals("/")){
                result = result / right;
            }
        }
        return result;
    }

    public Integer visitUnary(RJScriptParser.UnaryContext ctx) {
        if (ctx.MINUS() != null) {
            return -(visit(ctx.unary()));
        }else {
            return visit(ctx.primary());
        }
    }

    @Override
    public Integer visitPrimary(RJScriptParser.PrimaryContext ctx) {
        if (ctx.INT() != null) {
            return Integer.parseInt(ctx.INT().getText());
        } else if (ctx.IDENTIFIER() != null) {
            String varName = ctx.IDENTIFIER().getText();
            if (variables.containsKey(varName)) {
                return variables.get(varName);
            } else {
                throw new RuntimeException("Undefined variable: " + varName);
            }
        } else if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        return 0;
    }
}
