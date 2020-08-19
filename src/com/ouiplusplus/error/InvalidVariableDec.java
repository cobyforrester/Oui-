package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidVariableDec extends Error {
    public InvalidVariableDec() {
        super("Invalid Variable Declaration");
    }
    public InvalidVariableDec(Position start, Position end, String details) {
        super(start, end, "Invalid Variable Declaration", details);
    }
}