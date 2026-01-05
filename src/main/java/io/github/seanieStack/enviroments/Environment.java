package io.github.seanieStack.enviroments;

import io.github.seanieStack.interpreter.Function;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    Environment parent;

    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, Function> functions = new HashMap<>();

    public Environment(Environment parent) {
        this.parent = parent;
    }

    public Environment getParent() {
        return parent;
    }

    public Object get(String variableName) {
        if (variables.containsKey(variableName)){
            return variables.get(variableName);
        }
        else if (parent != null){
            return parent.get(variableName);
        }
        else {
            return null;
        }
    }

    public void put(String variableName, Object value) {
        variables.put(variableName, value);
    }

    public boolean hasVariable(String variableName) {
        if (variables.containsKey(variableName)){
            return true;
        }
        else if (parent != null){
            return parent.hasVariable(variableName);
        }
        else {
            return false;
        }
    }

    public void update(String variableName, Object value) {
        if (variables.containsKey(variableName)){
            variables.put(variableName, value);
        }
        else if (parent != null){
            parent.update(variableName, value);
        }
        else {
            throw new RuntimeException("Variable " + variableName + " not found");
        }
    }

    public void putFunction(String functionName, Function function) {
        functions.put(functionName, function);
    }

    public Function getFunction(String functionName) {
        if (functions.containsKey(functionName)){
            return functions.get(functionName);
        }
        else if (parent != null){
            return parent.getFunction(functionName);
        }
        else {
            return null;
        }
    }

    public boolean hasFunction(String functionName) {
        if (functions.containsKey(functionName)){
            return true;
        }
        else if (parent != null){
            return parent.hasFunction(functionName);
        }
        else {
            return false;
        }
    }
}
