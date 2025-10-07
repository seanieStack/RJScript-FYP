grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

//sample grammar taken from https://www.youtube.com/watch?v=FCfiCPIeE2Y used to setup antlr

fileStat : expr;

expr : additiveExpr ;

additiveExpr : additiveExpr ('+' | '-' ) primaryExpr
             | primaryExpr
             ;

primaryExpr : NUM ;

NUM: [0-9]+ ;
SPACES : [ \t] -> skip;