package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class UnclosedParenthesis extends Error {
    public UnclosedParenthesis() {
        super("Unclosed Parenthesis");
    }
    public UnclosedParenthesis(Position index, String details) {
        super(index, "Unclosed Parenthesis", details);
    }
}
