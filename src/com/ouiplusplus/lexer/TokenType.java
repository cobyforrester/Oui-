package com.ouiplusplus.lexer;

public enum TokenType {
    // BASE TYPES
    NULL,
    INT,
    DOUBLE,
    BOOLEAN,
    STRING,
    LIST,
    MAP,

    // OPERATIONS AND RELATED TO MATH
    PLUS,
    MINUS,
    MULT,
    DIV,
    EQUALS,
    CARROT,
    MODULO,
    PLUSEQUALS,
    MINUSEQUALS,

    /*
    BOOLEAN OPERATORS
     */
    // COMP
    DOUBLE_EQUALS,
    GREATER_THAN,
    GREATER_THAN_OR_EQUALS,
    LESS_THAN,
    LESS_THAN_OR_EQUALS,
    NOT_EQUAL,
    // AND_OR
    AND,
    OR,
    // NOT
    NOT,

    // (){}[]
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    LCBRACE,
    RCBRACE,
    CLOSEDPAREN, // for ASTExpression
    MAPEND, // for maps: x = $|10: 10|
    MAPSTART,

    //SPECIAL CHARACTERS
    COMMA,
    DOT,
    HASH,
    NEWLINE,
    SEMICOLON,
    RIGHTCARROT,
    COLON,

    // FUNCTIONS
    FUNCCALL,

    //LIST OF KEYWORDS
    WORD, //general word for first tokenizing
    VAR;

}