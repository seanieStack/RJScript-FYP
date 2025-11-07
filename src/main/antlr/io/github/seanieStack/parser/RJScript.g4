grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

program : statement+ EOF ;

statement : varDeclaration
          | varAssignment
          | printStatement
          | ifStatement
          | expressionStatement
          ;

varDeclaration : LET IDENTIFIER EQUALS expression SEMICOLON ;

varAssignment : IDENTIFIER EQUALS expression SEMICOLON ;

printStatement : PRINT LPAREN expression RPAREN SEMICOLON ;

ifStatement : IF LPAREN expression RPAREN block elseIfStatement* elseStatement? ;

elseIfStatement : ELSE IF LPAREN expression RPAREN block ;

elseStatement : ELSE block ;

block : LBRACE statement* RBRACE ;

expressionStatement : expression SEMICOLON ;

expression : comparison ;

comparison : additive ((LT | GT | LE | GE | EQ | NEQ) additive)? ;

additive : multiplicative ((PLUS | MINUS) multiplicative)*;

multiplicative: unary ((MULTIPLY | DIVISION) unary)*;

unary : MINUS unary
      | primary
      ;

primary : INT
        | BOOLEAN
        | IDENTIFIER
        | LPAREN expression RPAREN
        ;

//lexer rules
LET       : 'let' ;
PRINT     : 'print' ;
IF        : 'if' ;
ELSE      : 'else' ;
EQUALS    : '=' ;
EQ        : '==' ;
NEQ       : '!=' ;
LT        : '<' ;
GT        : '>' ;
LE        : '<=' ;
GE        : '>=' ;
PLUS      : '+' ;
MINUS     : '-' ;
MULTIPLY  : '*' ;
DIVISION  : '/' ;
LPAREN    : '(' ;
RPAREN    : ')' ;
LBRACE    : '{' ;
RBRACE    : '}' ;
SEMICOLON : ';' ;
BOOLEAN   : 'true' | 'false' ;
INT       : [0-9]+ ;
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]* ;
WS        : [ \t\r\n]+ -> skip ;
COMMENT   : '//' ~[\r\n]* -> skip ;