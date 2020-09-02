package com.ouiplusplus.parser;

import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.Token;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.lexer.TokenType;
import com.ouiplusplus.lexer.ValidateLexTokens;

import java.util.List;
import java.util.Stack;

public class ASTBoolean {
    private ASTExpression astExpr;
    private TGParser tgparser;

    public ASTBoolean(ASTExpression astExpr, TGParser tgparser) {
        this.astExpr = astExpr;
        this.tgparser = tgparser;
    }

    public Pair<Token, Error> process(List<Token> lst) {

        // Removes () in statements
        // NEED TO DEAL WITH NEGATIVE (
        boolean isNeg = false;
        while (lst.get(0).getType() == TokenType.LPAREN
                && lst.get(lst.size()).getType() == TokenType.RPAREN
                && ValidateLexTokens.validateParentheses(lst.subList(1, lst.size() - 1)) == null) {
            if (lst.get(0).isNeg()) isNeg = !isNeg;
            lst = lst.subList(1, lst.size() - 1);
        }

        // FOR &&, ||
        int index = 0;
        boolean bool = false;
        for (int i = 0; i < lst.size(); i++){
            //Stack so we can stay on the outermost
            Stack<Token> st = new Stack<>();
            if (lst.get(i).getType() == TokenType.LPAREN) st.push(lst.get(i));
            else if (lst.get(i).getType() == TokenType.RPAREN) st.pop();

            // recursive aspect, does comparison
            if (st.size() == 0
                    && (lst.get(i).getType() == TokenType.AND
                    || lst.get(i).getType() == TokenType.OR)) {
                Pair<Token, Error> rtnPair = this.process(lst.subList(index, i - 1));
                if (rtnPair.getP2() != null) return rtnPair;
                if(index == 0) bool = rtnPair.getP1().getBoolVal();
                else if(lst.get(i).getType() == TokenType.AND )
                    bool = bool && rtnPair.getP1().getBoolVal();
                else bool = bool || rtnPair.getP1().getBoolVal();
                index = i + 1;
            }
        }
        // for last section
        if (index != 0) {
            Pair<Token, Error> rtnPair = this.process(lst.subList(index, lst.size()));
        }

        // FOR ==, !=, > ...
        // make similar to above with stack etc

        // !!!!!! If after everything ==, &&, ||, !=, ! still inside then return error !!!!!!
        // else put into ast if not a boolean, because by here we are with an expression
        // adding on () if isNeg == true and then sending
        return null;
    }
}
