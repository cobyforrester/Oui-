package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class NullOperation extends Error {
    public NullOperation() {
        super("Null Operation");
    }
    public NullOperation(Position start, Position end, String details) {
        super(start, end, "Null Operation", details);
    }
}