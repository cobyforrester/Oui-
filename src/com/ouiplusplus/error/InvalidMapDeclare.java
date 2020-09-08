package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidMapDeclare extends Error {
    public InvalidMapDeclare() {
        super("Invalid Map Declare");
    }
    public InvalidMapDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid Map Declare", details);
    }
}