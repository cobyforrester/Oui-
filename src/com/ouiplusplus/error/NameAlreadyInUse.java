package com.ouiplusplus.error;

import com.ouiplusplus.lexer.Position;



public class NameAlreadyInUse extends Error {
    public NameAlreadyInUse() {
        super("Name Already in Use");
    }
    public NameAlreadyInUse(Position start, Position end, String details) {
        super(start, end, "Name Already in Use", details);
    }
}