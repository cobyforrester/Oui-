package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidElifDeclare extends Error {
    public InvalidElifDeclare() {
        super("Invalid Elif Declaration");
    }
    public InvalidElifDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid Elif Declaration", details);
    }
}