grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

program : statement+ EOF ;

statement : expression SEMICOLON ;

expression : additive ;

additive : primary ((PLUS | MINUS) primary)*;

primary : INT
        | LPAREN expression RPAREN
        ;

//lexer rules
PLUS      : '+' ;
MINUS     : '-' ;
LPAREN    : '(' ;
RPAREN    : ')' ;
SEMICOLON : ';' ;
INT       : [0-9]+ ;
WS        : [ \t\r\n]+ -> skip ;