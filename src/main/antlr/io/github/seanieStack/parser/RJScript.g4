grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

program : statement+ EOF ;

statement : expression SEMICOLON ;

expression : additive ;

additive : multiplicative ((PLUS | MINUS) multiplicative)*;

multiplicative: unary ((MULTIPLY | DIVISION) unary)*;

unary : MINUS unary
      | primary
      ;

primary : INT
        | LPAREN expression RPAREN
        ;

//lexer rules
PLUS      : '+' ;
MINUS     : '-' ;
MULTIPLY  : '*' ;
DIVISION  : '/' ;
LPAREN    : '(' ;
RPAREN    : ')' ;
SEMICOLON : ';' ;
INT       : [0-9]+ ;
WS        : [ \t\r\n]+ -> skip ;