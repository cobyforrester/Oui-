package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.helper.Pair;
import com.ouiplusplus.lexer.*;
import com.ouiplusplus.error.*;

import java.util.List;

public class AST {
    public TreeNode root;
    //############## CLASS METHODS #######################
    public AST() {
    }

    public Error addVal(String value) { //null for no errors
        if (value == null) return new Error(); //null value
        if (root == null) //if root is null
        {
            return this.caseRootNull(value);
        }
        else if  (isNumeric(value)) //value is num
        {
            if ("*/".contains(root.value)) return caseValNumRootMD(value);
            else if ("+-".contains(root.value)) return caseValNumRootPM(value);

        } else { //val is special character
            if (isDoubleOp(value)) return new Error(); // double op case
            switch (value) //check all values for value
            {
                case "+": //FALLS TO "-"
                case "-":
                    return caseValPM(value);
                case "*": //FALLS TO "/"
                case "/":
                    if ("*/".contains(root.value)) return caseValMDRootMD(value);
                    else if ("+-".contains(root.value)) return caseValMDRootPM(value);
                    else if(isNumeric(root.value)) return caseValMDRootNum(value);
                    else return new Error();

                default:
                    return new Error();
            }
        }
        return new Error();
    }

    public Error addList(List<Token> tokens) {
        for(int i = 0; i < tokens.size(); i++) {
            TokenType tmp = tokens.get(i).getType();
            String str;
            if (tmp == TokenType.FLOAT || tmp == TokenType.INT ) {
                str = String.valueOf(tokens.get(i).getValue());
                Error add = this.addVal(str);
                if(add != null) return add;
            } else {
                str = tokens.get(i).getType().value;
                Error add = this.addVal(str);
                if(add != null) return add;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.dfsToString(this.root);
    }

    //############## END CLASS METHODS ####################

    // ############## LIST OF ALL CASES ###################

    public Error caseRootNull(String value){
        if (!isNumeric(value)) return new Error(); //op first token
        this.root = new TreeNode(value);
        return null;
    }
    public Error caseValNumRootMD(String value){
        if (this.root.right != null) return new Error(); //double numbers
        this.root.right = new TreeNode(value);
        return null;
    }
    public Error caseValNumRootPM(String value){
        TreeNode tmpRoot = this.root;
        while (tmpRoot.right != null) tmpRoot = tmpRoot.right;
        if (tmpRoot.left == null) return new Error(); //tree without left
        tmpRoot.right = new TreeNode(value);
        return null;
    }
    public Error caseValPM(String value){
        if(this.root.right == null && !isNumeric(this.root.value)) return new Error();

        TreeNode tmp = new TreeNode(value);
        tmp.left = this.root;
        this.root = tmp;
        return null;
    }
    public Error caseValMDRootMD(String value){
        TreeNode tmp = new TreeNode(value);
        tmp.left = this.root;
        this.root = tmp;
        return null;
    }
    public Error caseValMDRootPM(String value){
        if (this.root.right == null) {
            return new Error();
        }
        //sets if root +- val */ makes right root tree pushed to left of new node
        //with value value and with left that of the original right tree
        //hard to describe
        TreeNode tmp = new TreeNode(value);
        tmp.left = this.root.right;
        this.root.right = tmp;
        return null;
    }
    public Error caseValMDRootNum(String value) {
        if (this.root.right != null) return new Error();
        TreeNode tmp = new TreeNode(value);
        tmp.left = this.root;
        this.root = tmp;
        return null;
    }
    // ################# END OF CASES ###########################


    // ################## HELPER METHODS #########################

    public String dfsToString(TreeNode node) { //assumed tree is valid
        String tmp = "";
        if(node.left != null) {
            tmp += "(" + this.dfsToString(node.left);
        }
        if (node.right != null) {
            tmp += node.value + this.dfsToString(node.right) + ")";
        }
        else return node.value;
        return tmp;
    }

    public boolean isDoubleOp(String value) {
        if("+-*/".contains(value)) { //catch for double op
            TreeNode tmpRoot = this.root;
            while (tmpRoot.right != null) tmpRoot = tmpRoot.right;
            if (tmpRoot.left != null) return true;
        }
        return false;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // ################### END HELPER METHODS #####################


    // ################### TREE CLASS #############################
    class TreeNode {
        public String value;
        public TreeNode right, left;

        public TreeNode(String value) {
            this.value = value;
        }
    }
    // #################### END TREE CLASS ###########################
}
