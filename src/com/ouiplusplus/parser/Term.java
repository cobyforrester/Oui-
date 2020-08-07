package com.ouiplusplus.parser;
import com.ouiplusplus.lexer.Token;

public class Term extends BinOp<NumberNode> {

    public Term(NumberNode left, NumberNode right, Token operation) {
        super(left, right, operation);
    }
    @Override
    public String toString() {
        String res = "";
        if(super.getLeft() == null) res = "";
        else if(super.getOperation() == null) res = super.getLeft().toString();
        else res = "(" + super.getLeft() + " " + super.getOperation() + " " + super.getRight() + ")";
        return res;
    }
}

