package io.github.seanieStack.interpreter;

import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.ast.core.ASTVisitor;
import io.github.seanieStack.ast.expressions.*;
import io.github.seanieStack.ast.statements.*;
import io.github.seanieStack.ast.structural.BlockNode;
import io.github.seanieStack.ast.structural.FunctionDeclarationNode;
import io.github.seanieStack.ast.structural.ProgramNode;
import io.github.seanieStack.constants.Constants;
import io.github.seanieStack.constants.ErrorMessages;
import io.github.seanieStack.environments.Environment;

/**
 * Interprets and executes an Abstract Syntax Tree (AST) by walking through nodes
 * and performing the operations they represent. This class implements the Visitor
 * pattern to traverse the AST and maintain execution state through an environment.
 */
public class ASTInterpreter implements ASTVisitor<Object> {

    private Environment env = new Environment(null);

    /**
     * Visits a program node and executes all statements in the program sequentially.
     *
     * @param node the program node containing all top-level statements
     * @return the result of the last statement executed, or null if no statements
     */
    @Override
    public Object visit(ProgramNode node) {
        Object result = null;
        for (ASTNode statement : node.statements()) {
            result = statement.accept(this);
        }
        return result;
    }

    /**
     * Visits a variable declaration node, evaluates the initialization expression,
     * and stores the variable in the current environment.
     *
     * @param node the variable declaration node containing the identifier and initialization expression
     * @return the value assigned to the variable
     */
    @Override
    public Object visit(VarDeclarationNode node) {
        Object value = node.expression().accept(this);
        env.put(node.identifier(), value);
        return value;
    }

    /**
     * Visits a variable assignment node, evaluates the new value expression,
     * and updates the existing variable in the environment.
     *
     * @param node the variable assignment node containing the identifier and new value expression
     * @return the new value assigned to the variable
     */
    @Override
    public Object visit(VarAssignmentNode node){
        Object value = node.expression().accept(this);
        env.update(node.identifier(), value);
        return value;
    }

    /**
     * Visits a print statement node, evaluates the expression, and outputs
     * the result to standard output.
     *
     * @param node the print statement node containing the expression to print
     * @return the value that was printed
     */
    @Override
    public Object visit(PrintStatementNode node) {
        Object value = node.expression().accept(this);
        System.out.println(value);
        return value;
    }

    /**
     * Visits an if statement node and evaluates the condition to determine which
     * block to execute. Processes else-if clauses sequentially and executes the
     * else block if no conditions are true.
     *
     * @param node the if statement node containing condition, then block, else-if clauses, and optional else block
     * @return the result of the executed block, or null if no block was executed
     */
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

    /**
     * Visits a while statement node and repeatedly executes the body block
     * as long as the condition evaluates to true.
     *
     * @param node the while statement node containing the condition and body block
     * @return the result of the last iteration of the loop body, or null if the loop never executed
     */
    @Override
    public Object visit(WhileStatementNode node) {
        Object result = null;
        while (toBoolean(node.condition().accept(this))) {
            result = node.body().accept(this);
        }
        return result;
    }

    /**
     * Visits a for statement node and executes a C-style for loop. Creates a new
     * environment scope for the loop, executes the initialization, then repeatedly
     * evaluates the condition and executes the body and update statement.
     *
     * @param node the for statement node containing initialization, condition, update, and body
     * @return the result of the last iteration of the loop body, or null if the loop never executed
     */
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

    /**
     * Visits a block node and executes all statements within a new environment scope.
     * Creates a child environment for the block to support lexical scoping, then
     * restores the parent environment after execution.
     *
     * @param node the block node containing statements to execute
     * @return the result of the last statement in the block, or null if the block is empty
     */
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

    /**
     * Visits an expression statement node and evaluates the contained expression.
     * This is used for expressions that appear as standalone statements.
     *
     * @param node the expression statement node
     * @return the result of evaluating the expression
     */
    @Override
    public Object visit(ExpressionStatementNode node) {
        return node.expression().accept(this);
    }

    /**
     * Visits a binary operation node, evaluates both operands, and performs the
     * specified arithmetic or comparison operation. Supports addition, subtraction,
     * multiplication, division, and comparison operators.
     *
     * @param node the binary operation node containing the operator and left/right operands
     * @return the result of the binary operation (Integer for arithmetic, Boolean for comparisons)
     */
    @Override
    public Object visit(BinaryOpNode node) {
        Object left = node.left().accept(this);
        Object right = node.right().accept(this);

        boolean isFloat = (left instanceof Double || right instanceof Double);

        return switch (node.operator()) {
            case ADD -> isFloat ? toDouble(left) + toDouble(right) : toInt(left) + toInt(right);
            case SUBTRACT -> isFloat ? toDouble(left) - toDouble(right) : toInt(left) - toInt(right);
            case MULTIPLY -> isFloat ? toDouble(left) * toDouble(right) : toInt(left) * toInt(right);
            case DIVIDE -> {
                if (isFloat) {
                    double rightVal = toDouble(right);
                    if (rightVal == 0.0) throw new RuntimeException(ErrorMessages.ERROR_DIVISION_BY_ZERO);
                    yield toDouble(left) / rightVal;
                } else {
                    int rightVal = toInt(right);
                    if (rightVal == 0) throw new RuntimeException(ErrorMessages.ERROR_DIVISION_BY_ZERO);
                    yield toInt(left) / rightVal;
                }
            }
            case LESS_THAN -> isFloat ? toDouble(left) < toDouble(right) : toInt(left) < toInt(right);
            case GREATER_THAN -> isFloat ? toDouble(left) > toDouble(right) : toInt(left) > toInt(right);
            case LESS_EQUAL -> isFloat ? toDouble(left) <= toDouble(right) : toInt(left) <= toInt(right);
            case GREATER_EQUAL -> isFloat ? toDouble(left) >= toDouble(right) : toInt(left) >= toInt(right);
            case EQUAL -> isFloat ? toDouble(left) == toDouble(right) : toInt(left) == toInt(right);
            case NOT_EQUAL -> isFloat ? toDouble(left) != toDouble(right) : toInt(left) != toInt(right);
        };
    }

    /**
     * Visits a unary operation node, evaluates the operand, and applies the
     * unary operator. Currently supports negation.
     *
     * @param node the unary operation node containing the operator and operand
     * @return the result of applying the unary operator
     */
    @Override
    public Object visit(UnaryOpNode node) {
        Object operand = node.operand().accept(this);
        return switch (node.operator()) {
            case NEGATE -> operand instanceof Double ? -toDouble(operand) : -toInt(operand);
        };
    }

    /**
     * Visits an integer literal node and returns its value.
     *
     * @param node the integer literal node
     * @return the integer value stored in the node
     */
    @Override
    public Object visit(IntLiteralNode node) {
        return node.value();
    }

    /**
     * Visits a boolean literal node and returns its value.
     *
     * @param node the boolean literal node
     * @return the boolean value stored in the node
     */
    @Override
    public Object visit(BoolLiteralNode node) {
        return node.value();
    }

    /**
     * Visits a variable reference node and retrieves the variable's value
     * from the current environment. Throws an exception if the variable is undefined.
     *
     * @param node the variable node containing the variable name
     * @return the current value of the variable
     * @throws RuntimeException if the variable is not defined in the environment
     */
    @Override
    public Object visit(VariableNode node) {
        if (env.hasVariable(node.name())) {
            return env.get(node.name());
        } else {
            throw new RuntimeException(ErrorMessages.ERROR_UNDEFINED_VARIABLE + node.name());
        }
    }

    @Override
    public Object visit(FloatLiteralNode node) {
        return node.value();
    }

    /**
     * Visits a function declaration node and stores the function definition in the
     * current environment. Captures the current environment as the closure for the function.
     *
     * @param node the function declaration node containing name, parameters, and body
     * @return null (function declarations don't produce a value)
     */
    @Override
    public Object visit(FunctionDeclarationNode node) {
        Function function = new Function(node.parameters(), node.body(), this.env);

        env.putFunction(node.name(), function);

        return null;
    }

    /**
     * Visits a function call node and executes the function with the provided arguments.
     * Creates a new environment that inherits from the function's closure environment,
     * binds arguments to parameters, executes the function body, and handles return values.
     *
     * @param node the function call node containing the function name and arguments
     * @return the value returned by the function, or null if no return statement was executed
     * @throws RuntimeException if the function is undefined or argument count doesn't match parameter count
     */
    @Override
    public Object visit(FunctionCallNode node) {
        if (!env.hasFunction(node.name())) {
            throw new RuntimeException(ErrorMessages.ERROR_UNDEFINED_FUNCTION + node.name());
        }

        Function function = env.getFunction(node.name());

        if (node.arguments().size() != function.parameters().size()) {
            throw new RuntimeException(String.format(ErrorMessages.ERROR_FUNCTION_ARGUMENT_MISMATCH,
                node.name(), function.parameters().size(), node.arguments().size()));
        }

        // Create a new environment that inherits from the function's closure environment,
        // enabling lexical scoping and access to variables from the function's definition context
        Environment functionEnv = new Environment(function.closureEnv());

        for (int i = 0; i < function.parameters().size(); i++) {
            String paramName = function.parameters().get(i);
            Object argValue = node.arguments().get(i).accept(this);
            functionEnv.put(paramName, argValue);
        }

        Environment previousEnv = this.env;
        this.env = functionEnv;

        Object result = null;
        try {
            function.body().accept(this);
        } catch (ReturnException returnEx) {
            result = returnEx.value();
        }

        this.env = previousEnv;

        return result;
    }

    /**
     * Visits a return statement node and throws a ReturnException containing the
     * evaluated return value. This exception is caught by the function call visitor
     * to implement early return from functions.
     *
     * @param node the return statement node containing the expression to return
     * @return never returns normally, always throws ReturnException
     * @throws ReturnException always thrown to implement control flow for function returns
     */
    @Override
    public Object visit(ReturnStatementNode node) {
        Object value = node.expression().accept(this);
        throw new ReturnException(value);
    }

    /**
     * Converts a value to an integer using type coercion rules. Integers are returned
     * as-is, booleans are converted to 1 (true) or 0 (false), floats are rounded. Other types cause an error.
     *
     * @param value the value to convert to an integer
     * @return the integer representation of the value
     * @throws RuntimeException if the value cannot be converted to an integer
     */
    private int toInt(Object value) {
        return switch (value) {
            case null -> throw new RuntimeException(ErrorMessages.ERROR_NULL_TO_INTEGER);
            case Integer i -> i;
            case Double d -> (int) Math.round(d);
            case Boolean b -> b ? Constants.TRUE_INTEGER_VALUE : Constants.FALSE_INTEGER_VALUE;
            default -> throw new RuntimeException(String.format(ErrorMessages.ERROR_CANNOT_CONVERT_TO_INTEGER, value));
        };
    }

    /**
     * Converts a value to a boolean using type coercion rules. Booleans are returned
     * as-is, non-zero integers are converted to true, and zero is converted to false.
     * Other types cause an error.
     *
     * @param value the value to convert to a boolean
     * @return the boolean representation of the value
     * @throws RuntimeException if the value cannot be converted to a boolean
     */
    private boolean toBoolean(Object value) {
        return switch (value) {
            case null -> throw new RuntimeException(ErrorMessages.ERROR_NULL_TO_BOOLEAN);
            case Boolean b -> b;
            case Integer i -> i != 0;
            case Double d -> d != 0.0;
            default -> throw new RuntimeException(String.format(ErrorMessages.ERROR_CANNOT_CONVERT_TO_BOOLEAN, value));
        };
    }

    /**
     * Converts a value to a double using type coercion rules. Floats are returned
     * as-is, integers are converted into floats with .0, booleans are 1.0 (true) or
     * 0.0 (flase), other types cause and error.
     *
     * @param value the value to convert to a double
     * @return  the double representation of the value
     * @throws RuntimeException if the value cannot be converted to a double
     */
    private double toDouble(Object value) {
        return switch (value){
            case null -> throw new RuntimeException(ErrorMessages.ERROR_NULL_TO_FLOAT);
            case  Double d -> d;
            case Integer i -> (double) i;
            case Boolean b -> b ? Constants.TRUE_FLOAT_VALUE : Constants.FALSE_FLOAT_VALUE;
            default -> throw new RuntimeException(String.format(ErrorMessages.ERROR_CANNOT_CONVERT_FLOAT, value));
        };
    }
}
