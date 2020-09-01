package com.ouiplusplus.lexer;

public class BeforeAfterLsts {

    /* This class if for what do do in each case when the lexer is making tokens */

    // NEED TO ADD ALL KEYWORDS WHEN THOUGHT OUT!!!!

    // ========================= PLUS ===============================
    final private TokenType[] beforePLUSAdd = {
            TokenType.INT, TokenType.DOUBLE, TokenType.STRING,
            TokenType.RPAREN,  TokenType.WORD, TokenType.EQUALS,

    };
    final private TokenType[] beforePLUSErr = {
            TokenType.NULL, TokenType.BOOLEAN, TokenType.SEMICOLON,
            TokenType.NEWLINE,
    };
    final private TokenType[] afterPLUSErr = {
            TokenType.NULL, TokenType.MULT, TokenType.DIV,
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.COMMA, TokenType.CARROT, TokenType.NEWLINE,
            TokenType.SEMICOLON,
    };

    // ========================= MINUS ===============================
    final private TokenType[] beforeMINUSAdd = {
             TokenType.INT, TokenType.DOUBLE,
            TokenType.RPAREN,  TokenType.WORD,


    };
    final private TokenType[] beforeMINUSErr = {
            TokenType.NULL, TokenType.BOOLEAN, TokenType.SEMICOLON,
            TokenType.STRING, TokenType.NEWLINE,
    };
    final private TokenType[] afterMINUSErr = {
            TokenType.NULL, TokenType.MULT, TokenType.DIV, TokenType.RPAREN,
            TokenType.RBRACKET, TokenType.RCBRACE, TokenType.COMMA, TokenType.CARROT,
            TokenType.NEWLINE,  TokenType.SEMICOLON,
    };

    // ========================= MULT/DIV ===============================
    final private TokenType[] beforeMULTDIVAdd = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.RPAREN, TokenType.WORD,
            TokenType.STRING,
    };

    final private TokenType[] afterMULTDIVAdd = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.LPAREN, TokenType.MINUS,
            TokenType.PLUS, TokenType.WORD, TokenType.STRING,
    };

    // ========================= INT/DOUBLE ===============================
    final private TokenType[] beforeINTDOUBLEAdd = {
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS,
            TokenType.MINUS, TokenType.WORD, TokenType.EQUALS,
            TokenType.BOOL_OPERATOR,
    };

    final private TokenType[] afterINTDOUBLEAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS,
            TokenType.MINUS, TokenType.NEWLINE, TokenType.SEMICOLON,
            TokenType.BOOL_OPERATOR,
    };

    // ========================= SEMICOLON ===============================
    final private TokenType[] beforeSEMICOLONAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.DOUBLE, TokenType.INT, TokenType.STRING,
            TokenType.WORD
    };

    final private TokenType[] afterSEMICOLONAdd = {
            TokenType.NEWLINE
    };

    // ========================= NEWLINE ===============================
    final private TokenType[] beforeNEWLINEAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.DOUBLE, TokenType.INT, TokenType.STRING,
            TokenType.WORD, TokenType.NEWLINE, TokenType.SEMICOLON
    };

    final private TokenType[] afterNEWLINEAdd = {
            TokenType.NEWLINE, TokenType.WORD,
    };

    // ========================= WORD ===============================
    final private TokenType[] beforeWORDAdd = {
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE,
            TokenType.WORD, TokenType.NEWLINE, TokenType.EQUALS,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS, TokenType.MINUS,
            TokenType.BOOL_OPERATOR,
    };

    final private TokenType[] afterWORDAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE,
            TokenType.WORD, TokenType.NEWLINE, TokenType.EQUALS,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS,
            TokenType.MINUS, TokenType.SEMICOLON, TokenType.BOOL_OPERATOR,

    };

    // ========================= STRING ===============================
    final private TokenType[] beforeSTRINGAdd = {
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE, TokenType.MULT,
            TokenType.PLUS, TokenType.WORD, TokenType.EQUALS, TokenType.BOOL_OPERATOR,
    };

    final private TokenType[] afterSTRINGAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.MULT, TokenType.PLUS, TokenType.NEWLINE,
            TokenType.SEMICOLON, TokenType.BOOL_OPERATOR,
    };

    // ========================= BOOL_OPS ===============================
    final private TokenType[] beforeBOOLOPAdd = {
            TokenType.INT, TokenType.DOUBLE,
             TokenType.RPAREN, TokenType.WORD,
            TokenType.STRING,
    };

    final private TokenType[] afterBOOLOPAdd = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.LPAREN, TokenType.MINUS,
            TokenType.PLUS, TokenType.WORD, TokenType.STRING,
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

    public TokenType[] getBeforeINTDOUBLEAdd() {
        return beforeINTDOUBLEAdd;
    }

    public TokenType[] getAfterINTDOUBLEAdd() {
        return afterINTDOUBLEAdd;
    }

    public TokenType[] getBeforeSEMICOLONAdd() {
        return beforeSEMICOLONAdd;
    }

    public TokenType[] getAfterSEMICOLONAdd() {
        return afterSEMICOLONAdd;
    }

    public TokenType[] getBeforeNEWLINEAdd() {
        return beforeNEWLINEAdd;
    }

    public TokenType[] getAfterNEWLINEAdd() {
        return afterNEWLINEAdd;
    }

    public TokenType[] getBeforeWORDAdd() {
        return beforeWORDAdd;
    }

    public TokenType[] getAfterWORDAdd() {
        return afterWORDAdd;
    }

    public TokenType[] getBeforeSTRINGAdd() {
        return beforeSTRINGAdd;
    }

    public TokenType[] getAfterSTRINGAdd() {
        return afterSTRINGAdd;
    }

    public TokenType[] getBeforeBOOLOPAdd() {
        return beforeBOOLOPAdd;
    }

    public TokenType[] getAfterBOOLOPAdd() {
        return afterBOOLOPAdd;
    }
}
