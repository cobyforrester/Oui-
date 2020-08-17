package com.ouiplusplus.lexer;

public class BeforeAfterLsts {

    /* This class if for what do do in each case when the lexer is making tokens */

    // NEED TO ADD ALL KEYWORDS WHEN THOUGHT OUT!!!!

    // ========================= PLUS ===============================
    final private TokenType[] beforePLUSAdd = {
            TokenType.FUNCCALL, TokenType.INT, TokenType.DOUBLE,
            TokenType.STRING, TokenType.RPAREN, TokenType.VAR,
    };
    final private TokenType[] beforePLUSErr = {
            TokenType.NULL, TokenType.BOOLEAN, TokenType.SEMICOLON,
    };
    final private TokenType[] afterPLUSErr = {
            TokenType.NULL, TokenType.MULT, TokenType.DIV, TokenType.RPAREN,
            TokenType.RBRACKET, TokenType.RCBRACE, TokenType.COMMA, TokenType.CARROT,
            TokenType.NEWLINE, TokenType.FUNCDECLARE, TokenType.SEMICOLON,
    };

    // ========================= MINUS ===============================
    final private TokenType[] beforeMINUSAdd = {
            TokenType.FUNCCALL, TokenType.INT, TokenType.DOUBLE,
            TokenType.STRING, TokenType.RPAREN, TokenType.VAR,
    };
    final private TokenType[] beforeMINUSErr = {
            TokenType.NULL, TokenType.BOOLEAN, TokenType.SEMICOLON,
            TokenType.STRING,
    };
    final private TokenType[] afterMINUSErr = {
            TokenType.NULL, TokenType.MULT, TokenType.DIV, TokenType.RPAREN,
            TokenType.RBRACKET, TokenType.RCBRACE, TokenType.COMMA, TokenType.CARROT,
            TokenType.NEWLINE, TokenType.FUNCDECLARE, TokenType.SEMICOLON,
    };

    // ========================= MULT/DIV ===============================
    final private TokenType[] beforeMULTDIVAdd = {
            TokenType.INT, TokenType.DOUBLE, TokenType.VAR,
            TokenType.FUNCCALL, TokenType.RPAREN,
    };

    final private TokenType[] afterMULTDIVAdd = {
            TokenType.INT, TokenType.DOUBLE, TokenType.VAR,
            TokenType.FUNCCALL, TokenType.LPAREN, TokenType.MINUS,
            TokenType.PLUS,
    };

    // ========================= INT/DOUBLE ===============================
    final private TokenType[] beforeINTDOUBLTAdd = {
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE, TokenType.MULT,
            TokenType.DIV, TokenType.PLUS, TokenType.MINUS, TokenType.NEWLINE,
    };

    final private TokenType[] afterINTDOUBLTAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE, TokenType.MULT,
            TokenType.DIV, TokenType.PLUS, TokenType.MINUS, TokenType.NEWLINE,
    };




    // ========================= CONSTRUCTOR ===============================
    public BeforeAfterLsts() {

    }

    // ========================= GETTERS ===============================
    public TokenType[] getBeforePLUSAdd() {
        return beforePLUSAdd;
    }

    public TokenType[] getBeforePLUSErr() {
        return beforePLUSErr;
    }

    public TokenType[] getAfterPLUSErr() {
        return afterPLUSErr;
    }

    public TokenType[] getBeforeMINUSAdd() {
        return beforeMINUSAdd;
    }

    public TokenType[] getBeforeMINUSErr() {
        return beforeMINUSErr;
    }

    public TokenType[] getAfterMINUSErr() {
        return afterMINUSErr;
    }

    public TokenType[] getBeforeMULTDIVAdd() {
        return beforeMULTDIVAdd;
    }

    public TokenType[] getAfterMULTDIVAdd() {
        return afterMULTDIVAdd;
    }

    public TokenType[] getBeforeINTDOUBLTAdd() {
        return beforeINTDOUBLTAdd;
    }

    public TokenType[] getAfterINTDOUBLTAdd() {
        return afterINTDOUBLTAdd;
    }
}
