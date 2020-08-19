package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;


public class UnresolvedName extends Error {
    public UnresolvedName() {
        super("Unresolved Name");
    }
    public UnresolvedName(Position start, Position end, String details) {
        super(start, end, "Unresolved Name", details);
    }
}