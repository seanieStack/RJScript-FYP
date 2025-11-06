package io.github.seanieStack.ast;

import java.util.List;

public final class IfStatementNode implements ASTNode {
    private final ASTNode condition;
    private final BlockNode thenBlock;
    private final List<ElseIfClause> elseIfClauses;
    private final BlockNode elseBlock;

    public IfStatementNode(ASTNode condition, BlockNode thenBlock, List<ElseIfClause> elseIfClauses, BlockNode elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseIfClauses = elseIfClauses;
        this.elseBlock = elseBlock;
    }

    public ASTNode condition() {
        return condition;
    }

    public BlockNode thenBlock() {
        return thenBlock;
    }

    public List<ElseIfClause> elseIfClauses() {
        return elseIfClauses;
    }

    public BlockNode elseBlock() {
        return elseBlock;
    }

    public static final class ElseIfClause {
        private final ASTNode condition;
        private final BlockNode block;

        public ElseIfClause(ASTNode condition, BlockNode block) {
            this.condition = condition;
            this.block = block;
        }

        public ASTNode condition() {
            return condition;
        }

        public BlockNode block() {
            return block;
        }

    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}