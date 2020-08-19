package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidFunctionDec extends Error {
    public InvalidFunctionDec() {
        super("Invalid Function Declaration");
    }
    public InvalidFunctionDec(Position start, Position end, String details) {
        super(start, end, "Invalid Function Declaration", details);
    }
}