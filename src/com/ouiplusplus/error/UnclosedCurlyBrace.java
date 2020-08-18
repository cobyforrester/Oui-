package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class UnclosedCurlyBrace extends Error {
    public UnclosedCurlyBrace() {
        super("Unclosed Curly Brace");
    }
    public UnclosedCurlyBrace(Position start, Position end, String details) {
        super(start, end, "Unclosed Curly Brace", details);
    }
}