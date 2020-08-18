package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;

public class UnexpectedChar extends Error {
    public UnexpectedChar() {
        super("Unexpected Character");
    }
    public UnexpectedChar(Position start, Position end, String details) {
        super(start, end, "Unexpected Character", details);
    }
}
