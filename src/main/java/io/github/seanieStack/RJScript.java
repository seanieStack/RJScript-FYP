package io.github.seanieStack;

import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.util.PrintAST;
import org.antlr.v4.runtime.*;

public class RJScript {
    public static void main(String[] args) {

        // REF: https://www.youtube.com/watch?v=FCfiCPIeE2Y
        String input = "67 + (85 - 5) + 10";
        CodePointCharStream inputStream = CharStreams.fromString(input);

        RJScriptLexer lexer = new RJScriptLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        RJScriptParser parser = new RJScriptParser(tokens);
        RJScriptParser.ProgramContext parseTree = parser.program();

        System.out.println(new PrintAST(parseTree));
    }
}
