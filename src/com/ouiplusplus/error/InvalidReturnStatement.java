package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidReturnStatement extends Error {
    public InvalidReturnStatement() {
        super("Invalid Return Statement");
    }
    public InvalidReturnStatement(Position start, Position end, String details) {
        super(start, end, "Invalid Return Statement", details);
    }
}