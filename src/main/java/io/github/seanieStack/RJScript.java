package io.github.seanieStack;

import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import io.github.seanieStack.util.PrintAST;
import org.antlr.v4.runtime.*;

public class RJScript {
    public static void main(String[] args) {

        // REF: https://www.youtube.com/watch?v=FCfiCPIeE2Y
        String input = "5838 + 44 - 3";
        CodePointCharStream inputStream = CharStreams.fromString(input);

        RJScriptLexer lexer = new RJScriptLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        RJScriptParser parser = new RJScriptParser(tokens);
        RJScriptParser.FileStatContext parseTree = parser.fileStat();

        System.out.println(new PrintAST(parseTree));
    }
}
