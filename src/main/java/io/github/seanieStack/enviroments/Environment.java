package io.github.seanieStack.enviroments;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    Environment parent;

    private final Map<String, Object> variables = new HashMap<>();

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
}
