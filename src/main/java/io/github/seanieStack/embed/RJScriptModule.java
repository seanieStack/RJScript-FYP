package io.github.seanieStack.embed;

import io.github.seanieStack.stdlib.NativeFunction;

import java.util.List;

public interface RJScriptModule {
    String name();
    List<NativeFunction> functions();
}
