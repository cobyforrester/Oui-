package com.ouiplusplus.parser;

import com.ouiplusplus.lexer.Token;

public class Expression extends BinOp<Term> {
    public Expression(Term left, Term right, Token operation) {
        super(left, right, operation);
    }
}
