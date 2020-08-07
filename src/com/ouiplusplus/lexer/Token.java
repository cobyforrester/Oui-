package com.ouiplusplus.lexer;
public class Token<T> {
    private TokenType type;
    private T value;
    public Token(TokenType type, T value) {
        this.type = type;
        this.value = value;
    }
    public Token(TokenType type) {
        this.type = type;
        this.value = null;
    }
    @Override
    public String toString() {
        if (this.value != null) {
            return this.type.toString() + ":" + this.value;
        }
        return this.type.toString();
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
