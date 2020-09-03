package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidIfDeclare extends Error {
    public InvalidIfDeclare() {
        super("Invalid If Declaration");
    }
    public InvalidIfDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid If Declaration", details);
    }
}