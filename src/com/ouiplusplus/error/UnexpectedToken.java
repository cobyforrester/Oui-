package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;

public class UnexpectedToken extends Error {
    public UnexpectedToken() {
        super("Unexpected Token");
    }
    public UnexpectedToken(Position start, Position end, String details) {
        super(start, end, "Unexpected Token", details);
    }
}
