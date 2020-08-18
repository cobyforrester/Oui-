package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;

public class UnexpectedToken extends Error {
    public UnexpectedToken() {
        super("Unexpected Token");
    }
    public UnexpectedToken(Position index, String details) {
        super(index, "Unexpected Token", details);
    }
}
