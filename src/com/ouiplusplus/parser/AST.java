package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.lexer.*;

import java.util.List;

public class AST {
    public TreeNode root;
    private Parser parser; //for functions and variables
    private int pos;

    //############## CLASS METHODS #######################
    public AST(Parser parser) {
        this.parser = parser;
        this.pos = -1;
    }

    public Error addVal(TokenType value) { //null for no errors
        Error err = new Error();

        return null;
    }

    public Error addList(List<Token> tokens) {

        return null;
    }


    //############## END CLASS METHODS ####################

    // ############## LIST OF ALL CASES ###################


    // ################# END OF CASES ###########################


    // ################## HELPER METHODS #########################


    // ################### END HELPER METHODS #####################


    // ################### TREE CLASS #############################
    class TreeNode {
        public TokenType value;
        public TreeNode right, left;

        public TreeNode(TokenType value) {
            this.value = value;
        }
    }
    // #################### END TREE CLASS ###########################
}
