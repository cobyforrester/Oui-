package com.ouiplusplus.lexer;


public enum TokenGroupType {
    EXPR,
    FUNC_DECLARE,
    FUNC_CALL,
    VAR_ASSIGN,
    PRINT,
    RETURN,

    //CONDITIONALS
    IF,
    ELIF,
    ELSE,

    //Loops
    WHILE,
    FOR;
}
