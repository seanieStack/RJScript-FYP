package io.github.seanieStack.environments;

import io.github.seanieStack.interpreter.Callable;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages variable and function bindings with support for lexical scoping.
 * Environments form a chain where each environment has an optional parent environment,
 * allowing for nested scopes and variable shadowing.
 */
public class Environment {
    private final Environment parent;

    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, Callable> functions = new HashMap<>();

    /**
     * Creates a new environment with the specified parent.
     *
     * @param parent the parent environment for scope chain, or null for the global scope
     */
    public Environment(Environment parent) {
        this.parent = parent;
    }

    /**
     * Returns the parent environment.
     *
     * @return the parent environment, or null if this is the global scope
     */
    public Environment getParent() {
        return parent;
    }

    /**
     * Looks up a variable by name, searching up the scope chain if necessary.
     *
     * @param variableName the name of the variable to look up
     * @return the variable's value, or null if not found
     */
    public Object get(String variableName) {
        if (variables.containsKey(variableName)) {
            return variables.get(variableName);
        } else if (parent != null) {
            return parent.get(variableName);
        } else {
            return null;
        }
    }

    /**
     * Defines a variable in the current scope.
     *
     * @param variableName the name of the variable
     * @param value the value to assign
     */
    public void put(String variableName, Object value) {
        variables.put(variableName, value);
    }

    /**
     * Checks if a variable exists in this scope or any parent scope.
     *
     * @param variableName the name of the variable to check
     * @return true if the variable exists, false otherwise
     */
    public boolean hasVariable(String variableName) {
        if (variables.containsKey(variableName)) {
            return true;
        } else if (parent != null) {
            return parent.hasVariable(variableName);
        } else {
            return false;
        }
    }

    /**
     * Updates an existing variable by searching up the scope chain.
     * Unlike put(), this throws an exception if the variable doesn't exist.
     *
     * @param variableName the name of the variable to update
     * @param value the new value to assign
     * @throws RuntimeException if the variable is not found in any scope
     */
    public void update(String variableName, Object value) {
        if (variables.containsKey(variableName)) {
            variables.put(variableName, value);
        } else if (parent != null) {
            parent.update(variableName, value);
        } else {
            throw new RuntimeException("Variable " + variableName + " not found");
        }
    }

    /**
     * Registers a callable (function or native function) in the current scope.
     *
     * @param functionName the name of the callable
     * @param callable the callable object to register
     */
    public void putFunction(String functionName, Callable callable) {
        functions.put(functionName, callable);
    }

    /**
     * Looks up a callable by name, searching up the scope chain if necessary.
     *
     * @param functionName the name of the callable to look up
     * @return the callable object, or null if not found
     */
    public Callable getFunction(String functionName) {
        if (functions.containsKey(functionName)) {
            return functions.get(functionName);
        } else if (parent != null) {
            return parent.getFunction(functionName);
        } else {
            return null;
        }
    }

    /**
     * Checks if a function exists in this scope or any parent scope.
     *
     * @param functionName the name of the function to check
     * @return true if the function exists, false otherwise
     */
    public boolean hasFunction(String functionName) {
        if (functions.containsKey(functionName)) {
            return true;
        } else if (parent != null) {
            return parent.hasFunction(functionName);
        } else {
            return false;
        }
    }
}
