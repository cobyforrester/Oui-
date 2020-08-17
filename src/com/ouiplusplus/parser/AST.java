package com.ouiplusplus.parser;
import com.ouiplusplus.error.Error;
import com.ouiplusplus.lexer.*;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Queue;


import java.util.List;

public class AST {
    /* DETAILS
    * PEMDAS WITH LEFT ON BOTTOM OF TREE
    * LPAREN AND INT/DOUBLE CAN BE NEGATIVE
    * LPAREN OPEN AT START THEN CLOSED LATER ON */
    public TreeNode root;
    private Parser parser; //for functions and variables
    private int pos;
    private int opened; //number of opened parentheses

    //############## CLASS METHODS #######################
    public AST(Parser parser) {
        this.parser = parser;
        this.pos = -1;
        this.opened = 0;
    }


    private Error addVal(Token token) { //null for no errors
        Error err = new Error();
        TokenType tt = token.getType();
        switch(tt) {

            // NUMBERS AND VARS
            case VAR: //FALLS TO INT
            case FUNCCALL:
            case DOUBLE:
            case INT: return caseNUM(token);

            // OPERATIONS
            case MULT://FALLS TO DOUBLE
            case DIV: return caseMULTDIV(token);
            case PLUS:
            case MINUS: return casePLUSMINUS(token);

            // PARENTHESIS
            case LPAREN: return caseLPAREN(token);
            case RPAREN: return caseRPAREN(token);

            // DEFAULT RETURN ERROR
            default: return err;


        }
    }

    public Error addList(List<Token> tokens) {
        for(Token tok: tokens) {
            if (this.addVal(tok) != null) {
                System.out.println("ERROR IN AST");
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (this.root == null) return "";
        return this.dfsToString(this.root);
    }



    //############## END CLASS METHODS ####################

    // ############## LIST OF ALL CASES ###################
    private Error caseLPAREN(Token token) {
        if (this.root == null) { //case root null
            this.root = new TreeNode(token);
            this.opened++;
            return null;
        }

        //setting currNode
        TreeNode currNode;
        if (this.opened != 0) currNode = this.returnBottomOpenParen();
        else currNode = this.root;

        //case LPAREN we are at has no leaves
        if (currNode.token.getType() == TokenType.LPAREN && currNode.left == null) {
            currNode.left = new TreeNode(token);
            this.opened++;
            return null;
        }

        //finding entry point and adding in value
        // Traverses down right side of tree until null right leaf found
        while (true) {
            if (currNode.right == null) {
                currNode.right = new TreeNode(token);
                this.opened++;
                return null;
            }
            currNode = currNode.right;
        }
    }
    private Error caseRPAREN(Token token) {
        Error err = new Error();
        if (this.root == null) return err;

        TreeNode currNode;
        if (this.opened != 0) {
            currNode = this.returnBottomOpenParen();
            currNode.token = new Token(TokenType.CLOSEDPAREN);
            if (currNode.token.isNeg()) currNode.token.setNeg(true);
            this.opened--;
            return null;
        }
        return err;
    }
    private Error caseNUM(Token token) {
        if (this.root == null) { //case root null
            this.root = new TreeNode(token);
            return null;
        }

        //setting currNode
        TreeNode currNode;
        if (this.opened != 0) currNode = this.returnBottomOpenParen();
        else currNode = this.root;

        //case LPAREN we are at has no leaves
        if (currNode.token.getType() == TokenType.LPAREN && currNode.left == null) {
            currNode.left = new TreeNode(token);
            return null;
        }


        //finding entry point and adding in value
        // Traverses down right side of tree until null right leaf found
        while (true) {
            if (currNode.right == null && isOp(currNode.token.getType())) {
                currNode.right = new TreeNode(token);
                return null;
            }
            if (isOp(currNode.token.getType())) {
                currNode = currNode.right;
            } else {
                currNode = currNode.left;
            }

        }
    }
    private Error caseMULTDIV(Token token) {
        //setting currNode
        TreeNode currNode;
        if (this.opened != 0) currNode = this.returnBottomOpenParen();
        else currNode = this.root;

        //finding entry point and adding in value
        // Traverses down right side of tree until null right leaf found
        while (true) {
            if (currNode.right == null) {
                TreeNode tmpLeft = currNode.left;
                if (currNode.token.getType() == TokenType.LPAREN) {
                    currNode.left = new TreeNode(token);
                } else {
                    Token tmp = currNode.token;
                    currNode.token = token;
                    currNode.left = new TreeNode(tmp);
                }
                currNode.left.left = tmpLeft;
                return null;
            }
            currNode = currNode.right;
        }
    }
    private Error casePLUSMINUS(Token token) {
        //setting currNode
        TreeNode currNode;
        if (this.opened != 0)  {
            currNode = this.returnBottomOpenParen();
            TreeNode tmpLeft = currNode.left;
            currNode.left = new TreeNode(token);
            currNode.left.left = tmpLeft;
        }
        else {
            TreeNode tmp = new TreeNode(token);
            tmp.left = this.root;
            this.root = tmp;
        }
        return null;
    }
    // ################# END OF CASES ###########################


    // ################## HELPER METHODS #########################

    public TreeNode returnBottomOpenParen() {
        if (this.opened == 0) return null;
        Queue<TreeNode> q = new LinkedList<>();
        int leftToDiscover = this.opened;
        q.add(this.root);
        while (q.size() != 0) {
            TreeNode curr = q.remove();
            //if LPAREN
            if (curr.token.getType() == TokenType.LPAREN) leftToDiscover--;
            if (leftToDiscover == 0) return curr;

            //adding left and right nodes
            if (curr.left != null) q.add(curr.left);
            if (curr.right != null) q.add(curr.right);
        }
        return null;
    }

    private static boolean isOp(TokenType tt) {
        if (tt == TokenType.MULT
                || tt == TokenType.DIV
                || tt == TokenType.MINUS
                || tt == TokenType.PLUS) {
            return true;
        }
        return false;
    }

    private String dfsToString(TreeNode node) {
        String tmp = "";
        if (node.right != null && node.left != null) {
            tmp += "("  + this.dfsToString(node.left);
            tmp += node.token + this.dfsToString(node.right) + ")";
        } else if (node.left != null) { // case of ()
            tmp += "(" + this.dfsToString(node.left) + ")";
        }
        else return "[" + node.token + "]";
        return tmp;
    }

    // ################### END HELPER METHODS #####################


    // ################### TREE CLASS #############################
    class TreeNode {
        public Token token;
        public TreeNode right, left;

        public TreeNode(Token token) {
            this.token = token;
        }
    }
    // #################### END TREE CLASS ###########################
}
