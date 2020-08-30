package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidType extends Error {
    public InvalidType() {
        super("Invalid Type");
    }
    public InvalidType(Position start, Position end, String details) {
        super(start, end, "Invalid Type", details);
    }
}