package com.ouiplusplus.error;
import com.ouiplusplus.lexer.Position;

public class OverFlow extends Error {
    public OverFlow() {
        super("Number Overflow Error!");
    }
    public OverFlow(Position index, String details) {
        super(index, "Number Overflow Error!", details);
    }
}
