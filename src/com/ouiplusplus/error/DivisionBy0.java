package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class DivisionBy0 extends Error {
    public DivisionBy0() {
        super("Division By 0");
    }
    public DivisionBy0(Position start, Position end, String details) {
        super(start, end, "Division By 0", details);
    }
}