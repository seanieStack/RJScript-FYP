package io.github.seanieStack.interpreter;

import io.github.seanieStack.ast.*;
import io.github.seanieStack.enviroments.Environment;

public class ASTInterpreter implements ASTVisitor<Object> {

    private Environment env = new Environment(null);

    @Override
    public Object visit(ProgramNode node) {
        Object result = null;
        for (ASTNode statement : node.statements()) {
            result = statement.accept(this);
        }
        return result;
    }

    @Override
    public Object visit(VarDeclarationNode node) {
        Object value = node.expression().accept(this);
        env.put(node.identifier(), value);
        return value;
    }

    @Override
    public Object visit(VarAssignmentNode node){
        Object value = node.expression().accept(this);
        env.update(node.identifier(), value);
        return value;
    }

    @Override
    public Object visit(PrintStatementNode node) {
        Object value = node.expression().accept(this);
        System.out.println(value);
        return value;
    }

    @Override
    public Object visit(IfStatementNode node) {
        Object condition = node.condition().accept(this);
        if (toBoolean(condition)) {
            return node.thenBlock().accept(this);
        }

        for (IfStatementNode.ElseIfClause elseIf : node.elseIfClauses()) {
            Object elseIfCondition = elseIf.condition().accept(this);
            if (toBoolean(elseIfCondition)) {
                return elseIf.block().accept(this);
            }
        }

        if (node.elseBlock() != null) {
            return node.elseBlock().accept(this);
        }

        return null;
    }

    @Override
    public Object visit(WhileStatementNode node) {
        Object result = null;
        while (toBoolean(node.condition().accept(this))) {
            result = node.body().accept(this);
        }
        return result;
    }

    @Override
    public Object visit(ForStatementNode node) {
        this.env = new Environment(this.env);

        node.initialization().accept(this);

        Object result = null;
        while (toBoolean(node.condition().accept(this))) {
            result = node.body().accept(this);
            node.update().accept(this);
        }

        this.env = env.getParent();
        return result;
    }

    @Override
    public Object visit(BlockNode node) {
        this.env = new Environment(this.env);
        Object result = null;
        for (ASTNode statement : node.statements()) {
            result = statement.accept(this);
        }
        this.env = env.getParent();
        return result;
    }

    @Override
    public Object visit(ExpressionStatementNode node) {
        return node.expression().accept(this);
    }

    @Override
    public Object visit(BinaryOpNode node) {
        Object left = node.left().accept(this);
        Object right = node.right().accept(this);

        return switch (node.operator()) {
            case ADD -> toInt(left) + toInt(right);
            case SUBTRACT -> toInt(left) - toInt(right);
            case MULTIPLY -> toInt(left) * toInt(right);
            case DIVIDE -> toInt(left) / toInt(right);
            case LESS_THAN -> toInt(left) < toInt(right);
            case GREATER_THAN -> toInt(left) > toInt(right);
            case LESS_EQUAL -> toInt(left) <= toInt(right);
            case GREATER_EQUAL -> toInt(left) >= toInt(right);
            case EQUAL -> toInt(left) == toInt(right);
            case NOT_EQUAL -> toInt(left) != toInt(right);
            default -> throw new RuntimeException("Unknown binary operator: " + node.operator());
        };
    }

    @Override
    public Object visit(UnaryOpNode node) {
        Object operand = node.operand().accept(this);
        return switch (node.operator()) {
            case NEGATE -> -toInt(operand);
            default -> throw new RuntimeException("Unknown unary operator: " + node.operator());
        };
    }

    @Override
    public Object visit(IntLiteralNode node) {
        return node.value();
    }

    @Override
    public Object visit(BoolLiteralNode node) {
        return node.value();
    }

    @Override
    public Object visit(VariableNode node) {
        if (env.hasVariable(node.name())) {
            return env.get(node.name());
        } else {
            throw new RuntimeException("Undefined variable: " + node.name());
        }
    }

    private int toInt(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1 : 0;
        }
        throw new RuntimeException("Cannot convert " + value + " to integer");
    }

    private boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Integer) {
            return ((Integer) value) != 0;
        }
        throw new RuntimeException("Cannot convert " + value + " to boolean");
    }
}
