package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidPrintStatement extends Error {
    public InvalidPrintStatement() {
        super("Invalid Print Statement");
    }
    public InvalidPrintStatement(Position start, Position end, String details) {
        super(start, end, "Invalid Print Statement", details);
    }
}