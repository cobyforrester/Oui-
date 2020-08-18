package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;

public class UnexpectedChar extends Error {
    public UnexpectedChar() {
        super("Unexpected Character");
    }
    public UnexpectedChar(Position index, String details) {
        super(index, "Unexpected Character", details);
    }
}
