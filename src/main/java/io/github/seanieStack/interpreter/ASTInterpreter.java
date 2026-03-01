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
import io.github.seanieStack.errors.ErrorType;
import io.github.seanieStack.errors.RJScriptError;
import io.github.seanieStack.stdlib.BuiltinModuleRegistry;
import io.github.seanieStack.stdlib.NativeFunction;
import io.github.seanieStack.stdlib.StandardLibrary;
import io.github.seanieStack.stdlib.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Interprets and executes an Abstract Syntax Tree (AST) by walking through nodes
 * and performing the operations they represent. This class implements the Visitor
 * pattern to traverse the AST and maintain execution state through an environment.
 */
public class ASTInterpreter implements ASTVisitor<Object> {

    private Environment env = new Environment(null);
    private final BinaryOperationEvaluator binaryOpEvaluator = new BinaryOperationEvaluator();
    private boolean traceEnabled = false;

    public ASTInterpreter() {
        StandardLibrary.register(env);
    }

    public void setTraceEnabled(boolean enabled) {
        this.traceEnabled = enabled;
    }

    public Environment getEnv() {
        return env;
    }

    private void trace(String msg) {
        if (traceEnabled) System.out.println(Constants.TRACE_PREFIX + msg);
    }

    /**
     * Visits a program node and executes all statements in the program sequentially.
     *
     * @param node the program node containing all top-level statements
     * @return the result of the last statement executed, or null if no statements
     */
    @Override
    public Object visit(ProgramNode node) {
        trace("program");
        Object result = null;
        for (ASTNode statement : node.statements()) {
            result = statement.accept(this);
        }
        return result;
    }

    /**
     * Visits an import statement node, resolves the function from the builtin module
     * registry, and registers it into the current environment.
     *
     * @param node the import statement node containing the function and module names
     * @return null (import statements don't produce a value)
     */
    @Override
    public Object visit(ImportStatementNode node) {
        trace("import " + node.functionName() + " from " + node.moduleName());
        try {
            NativeFunction function = BuiltinModuleRegistry.getFunction(node.moduleName(), node.functionName());
            env.putFunction(node.functionName(), function);
        } catch (RuntimeException e) {
            throw new RJScriptError(ErrorType.IMPORT, e.getMessage(), node.line(), node.column());
        }
        return null;
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
        trace("let " + node.identifier());
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
        trace("assign " + node.identifier());
        Object value = node.expression().accept(this);
        try {
            env.update(node.identifier(), value);
        } catch (RuntimeException e) {
            throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
        }
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
        trace("if");
        try {
            Object condition = node.condition().accept(this);
            if (TypeConverter.toBoolean(condition)) {
                return node.thenBlock().accept(this);
            }

            for (IfStatementNode.ElseIfClause elseIf : node.elseIfClauses()) {
                Object elseIfCondition = elseIf.condition().accept(this);
                if (TypeConverter.toBoolean(elseIfCondition)) {
                    return elseIf.block().accept(this);
                }
            }
        } catch (ReturnException | RJScriptError e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
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
        trace("while");
        Object result = null;
        try {
            while (TypeConverter.toBoolean(node.condition().accept(this))) {
                result = node.body().accept(this);
            }
        } catch (ReturnException | RJScriptError e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
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
        trace("for");
        this.env = new Environment(this.env);

        node.initialization().accept(this);

        Object result = null;
        try {
            while (TypeConverter.toBoolean(node.condition().accept(this))) {
                result = node.body().accept(this);
                node.update().accept(this);
            }
        } catch (ReturnException | RJScriptError e) {
            this.env = env.getParent();
            throw e;
        } catch (RuntimeException e) {
            this.env = env.getParent();
            throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
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
        trace("block");
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
        trace("expression-statement");
        return node.expression().accept(this);
    }

    /**
     * Visits a binary operation node, evaluates both operands, and delegates
     * the operation to the BinaryOperationEvaluator.
     *
     * @param node the binary operation node containing the operator and left/right operands
     * @return the result of the binary operation (Integer, Double, Boolean, or String)
     */
    @Override
    public Object visit(BinaryOpNode node) {
        trace("binop " + node.operator());
        Object left = node.left().accept(this);
        Object right = node.right().accept(this);
        try {
            return binaryOpEvaluator.evaluate(node.operator(), left, right, node.line(), node.column());
        } catch (RJScriptError e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
        }
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
        trace("unary " + node.operator());
        Object operand = node.operand().accept(this);
        if (node.operator() == UnaryOpNode.Operator.NEGATE) {
            try {
                if (operand instanceof Double) {
                    return -TypeConverter.toDouble(operand);
                } else {
                    return -TypeConverter.toInt(operand);
                }
            } catch (RuntimeException e) {
                throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
            }
        }
        throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_UNKNOWN_UNARY_OPERATOR + node.operator(), node.line(), node.column());
    }

    /**
     * Visits an integer literal node and returns its value.
     *
     * @param node the integer literal node
     * @return the integer value stored in the node
     */
    @Override
    public Object visit(IntLiteralNode node) {
        trace("int " + node.value());
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
        trace("bool " + node.value());
        return node.value();
    }

    /**
     * Visits a variable reference node and retrieves the variable's value
     * from the current environment. Throws an exception if the variable is undefined.
     *
     * @param node the variable node containing the variable name
     * @return the current value of the variable
     * @throws RJScriptError if the variable is not defined in the environment
     */
    @Override
    public Object visit(VariableNode node) {
        trace("var " + node.name());
        if (env.hasVariable(node.name())) {
            return env.get(node.name());
        } else {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_UNDEFINED_VARIABLE + node.name(), node.line(), node.column());
        }
    }

    @Override
    public Object visit(FloatLiteralNode node) {
        trace("float " + node.value());
        return node.value();
    }

    @Override
    public Object visit(StringLiteralNode node) {
        trace("string \"" + node.value() + "\"");
        return node.value();
    }

    /**
     * Visits an array literal node and evaluates all elements into an ArrayList.
     *
     * @param node the array literal node containing element expressions
     * @return an ArrayList containing the evaluated elements
     */
    @Override
    public Object visit(ArrayLiteralNode node) {
        trace("array[" + node.elements().size() + "]");
        ArrayList<Object> elements = new ArrayList<>();
        for (ASTNode element : node.elements()) {
            elements.add(element.accept(this));
        }
        return elements;
    }

    /**
     * Visits an index access node and retrieves the element at the specified index.
     * Supports negative indexing (Python-style) where -1 is the last element.
     * Supports chained indexing for nested arrays.
     *
     * @param node the index access node containing the identifier and indices
     * @return the value at the specified index position
     * @throws RJScriptError if the variable is not an array or index is out of bounds
     */
    @Override
    public Object visit(IndexAccessNode node) {
        trace("index " + node.identifier());
        if (!env.hasVariable(node.identifier())) {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_UNDEFINED_VARIABLE + node.identifier(), node.line(), node.column());
        }

        Object current = env.get(node.identifier());

        for (ASTNode indexNode : node.indices()) {
            if (!(current instanceof List<?> list)) {
                throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_CANNOT_INDEX_NON_ARRAY + TypeUtils.getTypeName(current), node.line(), node.column());
            }

            Object indexValue = indexNode.accept(this);

            current = getNextListInChain(list, indexValue, node.line(), node.column());
        }

        return current;
    }

    /**
     * Visits an indexed assignment node and modifies the element at the specified index.
     * Supports negative indexing and nested array assignment.
     *
     * @param node the indexed assignment node containing identifier, indices, and value
     * @return the assigned value
     * @throws RJScriptError if the variable is not an array or index is out of bounds
     */
    @Override
    public Object visit(IndexedAssignmentNode node) {
        trace("index-assign " + node.identifier());
        if (!env.hasVariable(node.identifier())) {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_UNDEFINED_VARIABLE + node.identifier(), node.line(), node.column());
        }

        Object current = env.get(node.identifier());

        // Navigate to the parent list for the final index
        for (int i = 0; i < node.indices().size() - 1; i++) {
            if (!(current instanceof List<?> list)) {
                throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_CANNOT_INDEX_NON_ARRAY + TypeUtils.getTypeName(current), node.line(), node.column());
            }

            Object indexValue = node.indices().get(i).accept(this);

            current = getNextListInChain(list, indexValue, node.line(), node.column());
        }

        // Now current is the list we need to modify
        if (!(current instanceof List<?>)) {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_CANNOT_INDEX_NON_ARRAY + TypeUtils.getTypeName(current), node.line(), node.column());
        }

        @SuppressWarnings("unchecked") //Checked above - reports unchecked due to wildcard check
        List<Object> targetList = (List<Object>) current;
        ASTNode lastIndexNode = node.indices().getLast();
        Object lastIndexValue = lastIndexNode.accept(this);

        if (!(lastIndexValue instanceof Integer)) {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_ARRAY_INDEX_NOT_INTEGER + TypeUtils.getTypeName(lastIndexValue), node.line(), node.column());
        }

        int lastIndex = (Integer) lastIndexValue;
        int actualLastIndex = resolveIndex(lastIndex, targetList.size());

        if (actualLastIndex < 0 || actualLastIndex >= targetList.size()) {
            throw new RJScriptError(ErrorType.RUNTIME, String.format(ErrorMessages.ERROR_ARRAY_INDEX_OUT_OF_BOUNDS, lastIndex, targetList.size()), node.line(), node.column());
        }

        Object value = node.value().accept(this);
        targetList.set(actualLastIndex, value);

        return value;
    }

    private Object getNextListInChain(List<?> list, Object indexValue, int line, int column) {
        Object current;
        if (!(indexValue instanceof Integer)) {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_ARRAY_INDEX_NOT_INTEGER + TypeUtils.getTypeName(indexValue), line, column);
        }

        int index = (Integer) indexValue;
        int actualIndex = resolveIndex(index, list.size());

        if (actualIndex < 0 || actualIndex >= list.size()) {
            throw new RJScriptError(ErrorType.RUNTIME, String.format(ErrorMessages.ERROR_ARRAY_INDEX_OUT_OF_BOUNDS, index, list.size()), line, column);
        }

        current = list.get(actualIndex);
        return current;
    }

    /**
     * Resolves a potentially negative index to an actual array index.
     * Negative indices count from the end: -1 is the last element, -2 is second-to-last, etc.
     *
     * @param index the index (may be negative)
     * @param length the length of the array
     * @return the resolved non-negative index
     */
    private int resolveIndex(int index, int length) {
        return index < 0 ? length + index : index;
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
        trace("fn " + node.name() + "(" + String.join(", ", node.parameters()) + ")");
        Function function = new Function(node.parameters(), node.body(), this.env);

        env.putFunction(node.name(), function);

        return null;
    }

    /**
     * Visits a function call node and executes the function with the provided arguments.
     * Handles both native functions (built-in) and user-defined functions.
     *
     * @param node the function call node containing the function name and arguments
     * @return the value returned by the function, or null if no return statement was executed
     * @throws RJScriptError if the function is undefined or argument count doesn't match parameter count
     */
    @Override
    public Object visit(FunctionCallNode node) {
        trace("call " + node.name() + "(" + node.arguments().size() + " args)");
        if (!env.hasFunction(node.name())) {
            throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_UNDEFINED_FUNCTION + node.name(), node.line(), node.column());
        }

        Callable callable = env.getFunction(node.name());

        if (callable instanceof NativeFunction nativeFunction) {
            return callNativeFunction(nativeFunction, node);
        } else if (callable instanceof Function function) {
            return callUserFunction(function, node);
        }

        throw new RJScriptError(ErrorType.RUNTIME, ErrorMessages.ERROR_UNDEFINED_FUNCTION + node.name(), node.line(), node.column());
    }

    private Object callNativeFunction(NativeFunction nativeFunction, FunctionCallNode node) {
        int expectedArity = nativeFunction.arity();
        int actualArgs = node.arguments().size();

        if (expectedArity != -1 && actualArgs != expectedArity) {
            throw new RJScriptError(ErrorType.RUNTIME, String.format(ErrorMessages.ERROR_NATIVE_FUNCTION_ARGUMENT_MISMATCH,
                nativeFunction.name(), expectedArity, actualArgs), node.line(), node.column());
        }

        List<Object> evaluatedArgs = new ArrayList<>();
        for (ASTNode arg : node.arguments()) {
            evaluatedArgs.add(arg.accept(this));
        }

        try {
            return nativeFunction.call(evaluatedArgs);
        } catch (RJScriptError e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RJScriptError(ErrorType.RUNTIME, e.getMessage(), node.line(), node.column());
        }
    }

    private Object callUserFunction(Function function, FunctionCallNode node) {
        if (node.arguments().size() != function.parameters().size()) {
            throw new RJScriptError(ErrorType.RUNTIME, String.format(ErrorMessages.ERROR_FUNCTION_ARGUMENT_MISMATCH,
                node.name(), function.parameters().size(), node.arguments().size()), node.line(), node.column());
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
        trace("return");
        Object value = node.expression().accept(this);
        throw new ReturnException(value);
    }
}
