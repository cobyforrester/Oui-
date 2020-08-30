package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class UnclosedString extends Error {
    public UnclosedString() {
        super("Unclosed String");
    }
    public UnclosedString(Position start, Position end, String details) {
        super(start, end, "Unclosed String", details);
    }
}