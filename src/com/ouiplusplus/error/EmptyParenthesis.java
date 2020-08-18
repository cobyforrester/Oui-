package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class EmptyParenthesis extends Error {
    public EmptyParenthesis() {
        super("Empty Parenthesis");
    }
    public EmptyParenthesis(Position start, Position end, String details) {
        super(start, end, "Empty Parenthesis", details);
    }
}