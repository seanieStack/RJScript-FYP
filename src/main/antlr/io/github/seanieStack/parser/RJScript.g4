grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

program : expression EOF ;

expression : additive ;

additive : primary ((PLUS | MINUS) primary)*;

primary : INT
        | LPAREN expression RPAREN
        ;

//lexer rules
PLUS   : '+' ;
MINUS  : '-' ;
LPAREN : '(' ;
RPAREN : ')' ;
INT    : [0-9]+ ;
WS     : [ \t]+ -> skip ;