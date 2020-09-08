package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;



public class IndexOutOfBounds extends Error {
    public IndexOutOfBounds() {
        super("Index Out of Bounds");
    }
    public IndexOutOfBounds(Position start, Position end, String details) {
        super(start, end, "Index Out of Bounds", details);
    }
}