package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class UnclosedCurlyBrace extends Error {
    public UnclosedCurlyBrace() {
        super("Unclosed Curly Brace");
    }
    public UnclosedCurlyBrace(Position index, String details) {
        super(index, "Unclosed Curly Brace", details);
    }
}