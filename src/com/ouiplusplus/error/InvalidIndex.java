package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;



public class InvalidIndex extends Error {
    public InvalidIndex() {
        super("Invalid Index Reference");
    }
    public InvalidIndex(Position start, Position end, String details) {
        super(start, end, "Invalid Index Reference", details);
    }
}