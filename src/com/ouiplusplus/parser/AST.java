package com.ouiplusplus.parser;

import com.ouiplusplus.error.Error;
import com.ouiplusplus.lexer.*;
import com.ouiplusplus.error.*;

public class AST {
    TreeNode root;

    public AST() {
    }
    public Error addVal(String value) { //null for no errors
        if (value == null) return new Error(); //null token
        if (root == null)
        {
            return this.caseRootNull(value);
        }
        else if (isNumeric(value)) //value is num
        {
             if ("*/".contains(root.value)) return caseValNumRootMD(value);
             else if ("+-".contains(root.value)) return caseValNumRootPM(value);

        }
        else if ("+-".contains(value))
        {
            return caseValPM(value);
        }
        else if ("*/".contains(value))
        {
             if ("*/".contains(root.value)) caseValMDRootMD(value);
             else if ("+-".contains(root.value)) return caseValMDRootPM(value);

        }
        return new Error();
    }

    // ######## LIST OF ALL CASES ################
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

    // ########## END OF CASES ####################

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


    class TreeNode {
        public String value;
        public TreeNode right, left;

        public TreeNode(String value) {
            this.value = value;
        }
    }
}
