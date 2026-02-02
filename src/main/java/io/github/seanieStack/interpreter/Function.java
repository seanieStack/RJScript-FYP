package io.github.seanieStack.interpreter;

import io.github.seanieStack.ast.structural.BlockNode;
import io.github.seanieStack.environments.Environment;

import java.util.List;

/**
 * Represents a user-defined function with support for lexical closures.
 * Captures the environment where the function was defined to enable proper variable scoping.
 */
public record Function(List<String> parameters, BlockNode body, Environment closureEnv) implements Callable {
    @Override
    public int arity() {
        return parameters.size();
    }
}