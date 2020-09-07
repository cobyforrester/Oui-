package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidForDeclare extends Error {
    public InvalidForDeclare() {
        super("Invalid For Loop Declaration");
    }
    public InvalidForDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid For Loop Declaration", details);
    }
}