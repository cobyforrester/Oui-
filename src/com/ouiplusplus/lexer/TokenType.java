package com.ouiplusplus.lexer;

public enum TokenType {
    // BASE TYPES
    NULL,
    INT,
    DOUBLE,
    BOOLEAN,
    STRING,

    // OPERATIONS AND RELATED TO MATH
    PLUS,
    MINUS,
    MULT,
    DIV,
    EQUALS,
    CARROT,

    // (){}[]
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    LCBRACE,
    RCBRACE,
    CLOSEDPAREN, // for ASTExpression

    //SPECIAL CHARACTERS
    COMMA,
    DOT,
    HASH,
    NEWLINE,
    SEMICOLON,

    // FUNCTIONS
    FUNCCALL,

    //LIST OF KEYWORDS
    WORD, //general word for first tokenizing
    VAR;

}