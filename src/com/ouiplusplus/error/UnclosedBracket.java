package com.ouiplusplus.error;


import com.ouiplusplus.lexer.Position;

public class UnclosedBracket extends Error {
    public UnclosedBracket() {
        super("Unclosed Bracket");
    }
    public UnclosedBracket(Position start, Position end, String details) {
        super(start, end, "Unclosed Bracket", details);
    }
}