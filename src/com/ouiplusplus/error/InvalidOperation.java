package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;



public class InvalidOperation extends Error {
    public InvalidOperation() {
        super("Invalid Operation");
    }
    public InvalidOperation(Position start, Position end, String details) {
        super(start, end, "Invalid Operation", details);
    }
}