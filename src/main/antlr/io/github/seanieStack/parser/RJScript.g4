grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

program : statement+ EOF ;

statement : varDeclaration
          | printStatement
          | expressionStatement
          ;

varDeclaration : LET IDENTIFIER EQUALS expression SEMICOLON ;

printStatement : PRINT LPAREN expression RPAREN SEMICOLON ;

expressionStatement : expression SEMICOLON ;

expression : additive ;

additive : multiplicative ((PLUS | MINUS) multiplicative)*;

multiplicative: unary ((MULTIPLY | DIVISION) unary)*;

unary : MINUS unary
      | primary
      ;

primary : INT
        | IDENTIFIER
        | LPAREN expression RPAREN
        ;

//lexer rules
LET       : 'let' ;
PRINT     : 'print' ;
EQUALS    : '=' ;
PLUS      : '+' ;
MINUS     : '-' ;
MULTIPLY  : '*' ;
DIVISION  : '/' ;
LPAREN    : '(' ;
RPAREN    : ')' ;
SEMICOLON : ';' ;
INT       : [0-9]+ ;
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]* ;
WS        : [ \t\r\n]+ -> skip ;
COMMENT   : '//' ~[\r\n]* -> skip ;