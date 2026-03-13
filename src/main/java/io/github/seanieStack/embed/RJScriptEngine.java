package io.github.seanieStack.embed;

import io.github.seanieStack.ast.builder.ASTBuilder;
import io.github.seanieStack.ast.core.ASTNode;
import io.github.seanieStack.interpreter.ASTInterpreter;
import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.stdlib.NativeFunction;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RJScriptEngine {

    private final Map<String, Map<String, NativeFunction>> embeddedModules = new HashMap<>();

    public void registerModule(RJScriptModule module) {
        if (embeddedModules.containsKey(module.name())) {
            throw new IllegalArgumentException("Module already registered: '" + module.name() + "'");
        }
        Map<String, NativeFunction> fns = new HashMap<>();
        for (NativeFunction fn : module.functions()) {
            fns.put(fn.name(), fn);
        }
        embeddedModules.put(module.name(), fns);
    }
    public void execute(String source) {
        CharStream inputStream = CharStreams.fromString(source);
        run(inputStream);
    }
    public void executeFile(String filePath) throws IOException {
        CharStream inputStream = CharStreams.fromFileName(filePath);
        run(inputStream);
    }

    private void run(CharStream inputStream) {
        RJScriptLexer lexer = new RJScriptLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RJScriptParser parser = new RJScriptParser(tokens);
        RJScriptParser.ProgramContext parseTree = parser.program();

        ASTBuilder astBuilder = new ASTBuilder();
        ASTNode ast = astBuilder.visit(parseTree);

        ASTInterpreter interpreter = new ASTInterpreter();
        interpreter.setEmbeddedModules(embeddedModules);
        ast.accept(interpreter);
    }
}
