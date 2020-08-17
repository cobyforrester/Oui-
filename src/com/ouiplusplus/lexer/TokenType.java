package com.ouiplusplus.lexer;

public enum TokenType {
    NULL,
    INT,
    DOUBLE,
    BOOLEAN,
    STRING,
    VAR,
    PLUS,
    MINUS,
    MULT,
    DIV,
    EQUALS,
    CARROT,
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    LCBRACE,
    RCBRACE,
    COMMA,
    DOT,
    SEMICOLON,
    FUNCDECLARE,
    FUNCCALL,
    HASH,
    NEWLINE,
    CLOSEDPAREN; // for AST

}