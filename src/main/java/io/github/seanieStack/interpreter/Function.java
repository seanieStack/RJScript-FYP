package io.github.seanieStack.interpreter;

import io.github.seanieStack.ast.BlockNode;
import io.github.seanieStack.enviroments.Environment;

import java.util.List;

public class Function {
    private final List<String> parameters;
    private final BlockNode body;
    private final Environment closureEnv;

    public Function(List<String> parameters, BlockNode body, Environment closureEnv) {
        this.parameters = parameters;
        this.body = body;
        this.closureEnv = closureEnv;
    }

    public List<String> parameters() {
        return parameters;
    }

    public BlockNode body() {
        return body;
    }

    public Environment closureEnv() {
        return closureEnv;
    }
}