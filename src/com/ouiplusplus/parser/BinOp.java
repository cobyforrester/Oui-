package com.ouiplusplus.parser;

import com.ouiplusplus.lexer.Token;

public class BinOp<T> {
    private T left, right;
    private Token operation;

    public BinOp(T left, T right, Token operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public String toString() {
        String res = "";
        if(this.left == null) res = "";
        else if(this.operation == null) res = this.left.toString();
        else res = "(" + this.left + " " + this.operation + " " + this.right + ")";
        return res;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public T getRight() {
        return right;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public Token getOperation() {
        return operation;
    }

    public void setOperation(Token operation) {
        this.operation = operation;
    }
}
