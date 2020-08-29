package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidVariableAssignment extends Error {
    public InvalidVariableAssignment() {
        super("Invalid Variable Assignment");
    }
    public InvalidVariableAssignment(Position start, Position end, String details) {
        super(start, end, "Invalid Variable Assignment", details);
    }
}