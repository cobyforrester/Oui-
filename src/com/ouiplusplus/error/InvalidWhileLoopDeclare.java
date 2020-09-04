package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;

public class InvalidWhileLoopDeclare extends Error {
    public InvalidWhileLoopDeclare() {
        super("Invalid While Loop Declaration");
    }
    public InvalidWhileLoopDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid While Loop Declaration", details);
    }
}
