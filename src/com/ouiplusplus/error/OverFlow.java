package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;

public class OverFlow extends Error {
    public OverFlow() {
        super("Number Overflow Error!");
    }
    public OverFlow(Position start, Position end, String details) {
        super(start, end, "Number Overflow Error!", details);
    }
}
