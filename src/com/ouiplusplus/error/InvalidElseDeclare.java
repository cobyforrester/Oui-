package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidElseDeclare extends Error {
    public InvalidElseDeclare() {
        super("Invalid Else Declaration");
    }
    public InvalidElseDeclare(Position start, Position end, String details) {
        super(start, end, "Invalid Else Declaration", details);
    }
}