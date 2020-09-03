package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidConditional extends Error {
    public InvalidConditional() {
        super("Invalid Conditional");
    }
    public InvalidConditional(Position start, Position end, String details) {
        super(start, end, "Invalid Conditional", details);
    }
}