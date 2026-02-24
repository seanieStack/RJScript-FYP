grammar RJScript;

@header {
    package io.github.seanieStack.parser;
}

program : statement+ EOF ;

statement : importStatement
          | varDeclaration
          | indexedAssignment
          | varAssignment
          | ifStatement
          | whileStatement
          | forStatement
          | functionDeclaration
          | returnStatement
          | expressionStatement
          ;

importStatement : IMPORT IDENTIFIER FROM IDENTIFIER SEMICOLON ;

varDeclaration : LET IDENTIFIER EQUALS expression SEMICOLON ;

varAssignment : IDENTIFIER EQUALS expression SEMICOLON ;

indexedAssignment : IDENTIFIER (LBRACKET expression RBRACKET)+ EQUALS expression SEMICOLON ;

ifStatement : IF LPAREN expression RPAREN block elseIfStatement* elseStatement? ;

elseIfStatement : ELSE IF LPAREN expression RPAREN block ;

elseStatement : ELSE block ;

whileStatement : WHILE LPAREN expression RPAREN block ;

forStatement : FOR LPAREN forInit expression SEMICOLON forUpdate RPAREN block ;

forInit : (LET IDENTIFIER EQUALS expression | IDENTIFIER EQUALS expression) SEMICOLON ;

forUpdate : IDENTIFIER EQUALS expression ;

functionDeclaration : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN block ;

parameterList : IDENTIFIER (COMMA IDENTIFIER)* ;

returnStatement : RETURN expression SEMICOLON ;

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
        | FLOAT
        | BOOLEAN
        | STRING_LITERAL
        | arrayLiteral
        | functionCall
        | indexAccess
        | IDENTIFIER
        | LPAREN expression RPAREN
        ;

arrayLiteral : LBRACKET (expression (COMMA expression)*)? RBRACKET ;

indexAccess : IDENTIFIER (LBRACKET expression RBRACKET)+ ;

functionCall : IDENTIFIER LPAREN argumentList? RPAREN ;

argumentList : expression (COMMA expression)* ;

//lexer rules
IMPORT          : 'import' ;
FROM            : 'from' ;
LET             : 'let' ;
IF              : 'if' ;
ELSE            : 'else' ;
WHILE           : 'while' ;
FOR             : 'for' ;
FUNCTION        : 'function' ;
RETURN          : 'return' ;
EQUALS          : '=' ;
EQ              : '==' ;
NEQ             : '!=' ;
LT              : '<' ;
GT              : '>' ;
LE              : '<=' ;
GE              : '>=' ;
PLUS            : '+' ;
MINUS           : '-' ;
MULTIPLY        : '*' ;
DIVISION        : '/' ;
LPAREN          : '(' ;
RPAREN          : ')' ;
LBRACE          : '{' ;
RBRACE          : '}' ;
LBRACKET        : '[' ;
RBRACKET        : ']' ;
SEMICOLON       : ';' ;
COMMA           : ',' ;
BOOLEAN         : 'true' | 'false' ;
STRING_LITERAL  : '"' (~["\\\r\n] | '\\' .)* '"' ;
FLOAT           : [0-9]+ '.' [0-9]+;
INT             : [0-9]+ ;
IDENTIFIER      : [a-zA-Z_][a-zA-Z0-9_]* ;
WS              : [ \t\r\n]+ -> skip ;
COMMENT         : '//' ~[\r\n]* -> skip ;