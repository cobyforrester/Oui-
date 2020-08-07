package com.ouiplusplus.error;
import com.ouiplusplus.position.Position;

public class UnexpectedChar extends Error {
    public UnexpectedChar() {
        super();
    }
    public UnexpectedChar(Position start, Position end, String details) {
        super(start, end, "Unexpected Character", details);
    }
}
