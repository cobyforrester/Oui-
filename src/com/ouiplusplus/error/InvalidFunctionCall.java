package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidFunctionCall extends Error {
    public InvalidFunctionCall() {
        super("Invalid Function Call");
    }
    public InvalidFunctionCall(Position start, Position end, String details) {
        super(start, end, "Invalid Function Call", details);
    }
}