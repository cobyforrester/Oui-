package com.ouiplusplus.lexer;

public class BeforeAfterLsts {

    /* This class if for what do do in each case when the lexer is making tokens */

    // NEED TO ADD ALL KEYWORDS WHEN THOUGHT OUT!!!!

    // ========================= PLUS ===============================
    final private TokenType[] beforePLUSAdd = {
            TokenType.INT, TokenType.DOUBLE, TokenType.STRING,
            TokenType.RPAREN,  TokenType.WORD, TokenType.EQUALS,
            TokenType.RBRACKET, TokenType.MAPEND,
    };
    final private TokenType[] beforePLUSErr = {
            TokenType.NULL, TokenType.BOOLEAN, TokenType.SEMICOLON,
            TokenType.NEWLINE,
    };
    final private TokenType[] afterPLUSErr = {
            TokenType.NULL, TokenType.MULT, TokenType.DIV,
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.COMMA, TokenType.CARROT, TokenType.NEWLINE,
            TokenType.SEMICOLON, TokenType.MODULO, TokenType.CARROT,
    };

    // ========================= MINUS ===============================
    final private TokenType[] beforeMINUSAdd = {
             TokenType.INT, TokenType.DOUBLE,
            TokenType.RPAREN,  TokenType.WORD,
            TokenType.RBRACKET,
    };
    final private TokenType[] beforeMINUSErr = {
            TokenType.NULL, TokenType.BOOLEAN, TokenType.SEMICOLON,
            TokenType.STRING, TokenType.NEWLINE,
    };
    final private TokenType[] afterMINUSErr = {
            TokenType.NULL, TokenType.MULT, TokenType.DIV, TokenType.RPAREN,
            TokenType.RBRACKET, TokenType.RCBRACE, TokenType.COMMA, TokenType.CARROT,
            TokenType.NEWLINE,  TokenType.SEMICOLON,
            TokenType.MODULO,
    };

    // ========================= MULT/DIV/MOD/^ ===============================
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
            TokenType.MINUSEQUALS, TokenType.PLUSEQUALS,
            TokenType.MODULO, TokenType.CARROT, TokenType.COMMA,
            TokenType.LBRACKET, TokenType.MAPSTART, TokenType.MAPSTART,
            TokenType.COLON,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.NOT, TokenType.AND, TokenType.OR,
    };

    final private TokenType[] afterINTDOUBLEAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.SEMICOLON,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS,
            TokenType.MINUS, TokenType.NEWLINE, TokenType.RCBRACE,
            TokenType.MODULO, TokenType.CARROT, TokenType.COMMA,
            TokenType.MAPEND, TokenType.MAPSTART, TokenType.COLON,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.NOT, TokenType.AND, TokenType.OR,
    };

    // ========================= SEMICOLON ===============================
    final private TokenType[] beforeSEMICOLONAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.DOUBLE, TokenType.INT, TokenType.STRING,
            TokenType.WORD, TokenType.BOOLEAN, TokenType.NULL,
            TokenType.MAPEND,
    };

    final private TokenType[] afterSEMICOLONAdd = {
            TokenType.NEWLINE
    };

    // ========================= NEWLINE ===============================
    final private TokenType[] beforeNEWLINEAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.DOUBLE, TokenType.INT, TokenType.STRING,
            TokenType.WORD, TokenType.NEWLINE, TokenType.SEMICOLON,
            TokenType.BOOLEAN, TokenType.LCBRACE, TokenType.HASH,
            TokenType.NULL, TokenType.MAPEND,
    };

    final private TokenType[] afterNEWLINEAdd = {
            TokenType.NEWLINE, TokenType.WORD, TokenType.LCBRACE,
            TokenType.RCBRACE, TokenType.HASH,
    };

    // ========================= COMMA ===============================
    final private TokenType[] beforeCOMMAAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.RCBRACE,
            TokenType.DOUBLE, TokenType.INT, TokenType.STRING,
            TokenType.WORD,  TokenType.SEMICOLON,
            TokenType.BOOLEAN, TokenType.LCBRACE, TokenType.NULL,
            TokenType.MAPEND, TokenType.MAPSTART,
    };

    final private TokenType[] afterCOMMAAdd = {
            TokenType.LPAREN, TokenType.LBRACKET,
            TokenType.DOUBLE, TokenType.INT, TokenType.STRING,
            TokenType.WORD, TokenType.BOOLEAN, TokenType.LCBRACE,
            TokenType.NOT, TokenType.MINUS, TokenType.PLUS,
            TokenType.NULL, TokenType.MAPEND, TokenType.MAPSTART,
    };

    // ========================= WORD ===============================
    final private TokenType[] beforeWORDAdd = {
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE,
            TokenType.WORD, TokenType.NEWLINE, TokenType.EQUALS,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS,
            TokenType.MINUS, TokenType.RCBRACE,
            TokenType.MINUSEQUALS, TokenType.PLUSEQUALS,
            TokenType.MODULO, TokenType.CARROT, TokenType.COMMA,
            TokenType.MAPSTART, TokenType.COLON,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.NOT, TokenType.AND, TokenType.OR,
    };

    final private TokenType[] afterWORDAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.LPAREN,
            TokenType.LCBRACE, TokenType.SEMICOLON,
            TokenType.WORD, TokenType.NEWLINE, TokenType.EQUALS,
            TokenType.MULT, TokenType.DIV, TokenType.PLUS,
            TokenType.MINUS, TokenType.RCBRACE,
            TokenType.MINUSEQUALS, TokenType.PLUSEQUALS,
            TokenType.MODULO, TokenType.CARROT, TokenType.COMMA,
            TokenType.INT, TokenType.BOOLEAN, TokenType.DOUBLE,
            TokenType.STRING, TokenType.NULL, TokenType.MAPEND,
            TokenType.COLON,


            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.AND, TokenType.OR,

    };

    // ========================= STRING ===============================
    final private TokenType[] beforeSTRINGAdd = {
            TokenType.LPAREN, TokenType.LBRACKET, TokenType.LCBRACE, TokenType.MULT,
            TokenType.PLUS, TokenType.WORD, TokenType.EQUALS,
            TokenType.MINUSEQUALS, TokenType.PLUSEQUALS,
            TokenType.COMMA, TokenType.MAPSTART, TokenType.COLON,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.AND, TokenType.OR, TokenType.NOT,
    };

    final private TokenType[] afterSTRINGAdd = {
            TokenType.RPAREN, TokenType.RBRACKET, TokenType.SEMICOLON,
            TokenType.MULT, TokenType.PLUS, TokenType.NEWLINE,
            TokenType.RCBRACE, TokenType.COMMA, TokenType.MAPEND,
            TokenType.COLON,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.AND, TokenType.OR,
    };

    // ========================= BOOL_COMP_OPS ===============================
    final private TokenType[] beforeBOOL_COMP_Add = {
            TokenType.INT, TokenType.DOUBLE,
             TokenType.RPAREN, TokenType.WORD,
            TokenType.STRING, TokenType.BOOLEAN,
            TokenType.LBRACKET, TokenType.RBRACKET,
            TokenType.NULL, TokenType.MINUS,
            TokenType.MAPEND,
    };

    final private TokenType[] afterBOOL_COMP_Add = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.LPAREN, TokenType.MINUS,
            TokenType.PLUS, TokenType.WORD, TokenType.STRING,
            TokenType.BOOLEAN, TokenType.LBRACKET, TokenType.RBRACKET,
            TokenType.NULL, TokenType.MAPSTART,
    };

    // ========================= AND_OR_OPS ===============================
    final private TokenType[] beforeAND_OR_Add = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.RPAREN, TokenType.WORD,
            TokenType.STRING, TokenType.BOOLEAN,
            TokenType.BOOLEAN, TokenType.LBRACKET, TokenType.RBRACKET,
            TokenType.NULL, TokenType.MAPEND,
    };

    final private TokenType[] afterAND_OR_Add = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.LPAREN, TokenType.MINUS,
            TokenType.PLUS, TokenType.WORD, TokenType.STRING,
            TokenType.BOOLEAN, TokenType.NOT,
            TokenType.LBRACKET, TokenType.RBRACKET,
            TokenType.NULL, TokenType.MAPSTART,
    };

    // ========================= NOT_OP ===============================
    final private TokenType[] beforeNOTAdd = {
            TokenType.LPAREN, TokenType.EQUALS,
            TokenType.AND, TokenType.OR,
            TokenType.LBRACKET, TokenType.COLON,
            TokenType.MAPSTART, TokenType.COMMA,
    };

    final private TokenType[] afterNOTAdd = {
            TokenType.INT, TokenType.DOUBLE,
            TokenType.LPAREN, TokenType.MINUS,
            TokenType.PLUS, TokenType.WORD, TokenType.STRING,
            TokenType.BOOLEAN, TokenType.LBRACKET, TokenType.RBRACKET,
            TokenType.NULL,
    };

    // ========================= BOOLEAN ===============================
    final private TokenType[] beforeBOOLEANAdd = {
            TokenType.LPAREN, TokenType.EQUALS, TokenType.COMMA,
            TokenType.LBRACKET, TokenType.MAPSTART,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.AND, TokenType.OR, TokenType.NOT,
    };

    final private TokenType[] afterBOOLEANAdd = {
            TokenType.NEWLINE, TokenType.SEMICOLON, TokenType.RPAREN,
            TokenType.COMMA, TokenType.RBRACKET, TokenType.MAPEND,

            //BOOLEAN COMP OPERATORS
            TokenType.DOUBLE_EQUALS, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUALS,
            TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUALS, TokenType.NOT_EQUAL,
            TokenType.AND, TokenType.OR, TokenType.NOT,
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

    public TokenType[] getBeforeBOOL_COMP_Add() {
        return beforeBOOL_COMP_Add;
    }

    public TokenType[] getAfterBOOL_COMP_Add() {
        return afterBOOL_COMP_Add;
    }

    public TokenType[] getBeforeAND_OR_Add() {
        return beforeAND_OR_Add;
    }

    public TokenType[] getAfterAND_OR_Add() {
        return afterAND_OR_Add;
    }

    public TokenType[] getBeforeNOTAdd() {
        return beforeNOTAdd;
    }

    public TokenType[] getAfterNOTAdd() {
        return afterNOTAdd;
    }

    public TokenType[] getBeforeBOOLEANAdd() {
        return beforeBOOLEANAdd;
    }

    public TokenType[] getAfterBOOLEANAdd() {
        return afterBOOLEANAdd;
    }

    public TokenType[] getBeforeCOMMAAdd() {
        return beforeCOMMAAdd;
    }

    public TokenType[] getAfterCOMMAAdd() {
        return afterCOMMAAdd;
    }
}
