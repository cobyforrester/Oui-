package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidVariableCall extends Error {
    public InvalidVariableCall() {
        super("Invalid Variable Call");
    }
    public InvalidVariableCall(Position start, Position end, String details) {
        super(start, end, "Invalid Variable Call", details);
    }
}
