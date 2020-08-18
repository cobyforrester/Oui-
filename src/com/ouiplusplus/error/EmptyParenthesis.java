package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class EmptyParenthesis extends Error {
    public EmptyParenthesis() {
        super("Empty Parenthesis");
    }
    public EmptyParenthesis(Position index, String details) {
        super(index, "Empty Parenthesis", details);
    }
}