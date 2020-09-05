package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class InvalidFunctionDeclaration extends Error {
    public InvalidFunctionDeclaration() {
        super("Invalid Function Declaration");
    }
    public InvalidFunctionDeclaration(Position start, Position end, String details) {
        super(start, end, "Invalid Function Declaration", details);
    }
}