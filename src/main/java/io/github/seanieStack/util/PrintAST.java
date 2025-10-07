package io.github.seanieStack.util;

import io.github.seanieStack.parser.RJScriptLexer;
import io.github.seanieStack.parser.RJScriptParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
// REF: https://www.youtube.com/watch?v=FCfiCPIeE2Y, this tree printer is completely copied from the video used for basic setup and may be used in feature for debugging
public class PrintAST {

    ParseTree tree;
    public PrintAST(ParseTree tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return treeString(tree, "");
    }

    private String treeString(ParseTree node, String prefix) {
        if(node instanceof RJScriptParser.PrimaryContext && node.getChildCount() == 1) return visitPrimary((RJScriptParser.PrimaryContext) node);
        if(node instanceof TerminalNode) return visitTerminal((TerminalNode) node);
        if(!(node instanceof RuleNode)) return "";

        var name = RJScriptParser.ruleNames[((RuleNode) node).getRuleContext().getRuleIndex()];
        StringBuilder sb = new StringBuilder(name);

        for(int i = 0; i < node.getChildCount(); i++){
            boolean atEnd = (i == node.getChildCount() - 1);
            String symbol = atEnd ? "└──" : "├──";

            ParseTree child = node.getChild(i);
            String childSymbol = atEnd ? " " : "│";

            String childString = treeString(child, prefix + childSymbol + "\t");

            sb.append("\n")
                    .append(prefix)
                    .append(symbol)
                    .append(" ")
                    .append(childString);
        }
        return sb.toString();
    }

    private String visitPrimary(RJScriptParser.PrimaryContext node) {
        String name = RJScriptParser.ruleNames[node.getRuleContext().getRuleIndex()];
        String childString = visitTerminal((TerminalNode) node.getChild(0));
        return name + " ── " + childString;
    }

    private String visitTerminal(TerminalNode node) {
        if(node.getSymbol().getType() == Token.EOF)  return "EOF";
        String id = RJScriptLexer.ruleNames[node.getSymbol().getType() - 1];

        return id + "'" + node.getText() + "'";
    }
}
