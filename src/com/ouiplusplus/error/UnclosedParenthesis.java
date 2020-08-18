package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class UnclosedParenthesis extends Error {
    public UnclosedParenthesis() {
        super("Unclosed Parenthesis");
    }
    public UnclosedParenthesis(Position start, Position end, String details) {
        super(start, end, "Unclosed Parenthesis", details);
    }
}
