/*
 * LocalSimplifier.java
 *
 * Created on November 7, 2001, 9:37 AM
 */

package ast.simplifier;

import ast.parser.*;
import tools.*;
import java.lang.*;
import java.util.*;

/**
 *
 * @author  administrator
 * @version 
 */
public class LocalSimplifier extends Object {

    /** Creates new LocalSimplifier */
    public LocalSimplifier() {
    }
    
    static AstNode Evaluate(AstNode expr) throws AssertionError {
        if (!(expr instanceof OperatorAst)) return expr;
        OperatorAst oa = (OperatorAst) expr;
        int num = oa.GetNumOperands();
        if (num != 2) return expr;
        AstNode op1 = Evaluate((AstNode) oa.GetOperand(0));
        AstNode op2 = Evaluate((AstNode) oa.GetOperand(1));
        if (!(op1 instanceof ConstAst && op2 instanceof ConstAst))
            return expr;
        ConstAst c1 = (ConstAst) op1;
        ConstAst c2 = (ConstAst) op2;
        if (!(c1.IsIntConst() && c2.IsIntConst()))
            return expr;
        String op = oa.GetOperator().GetOpName();
        Integer np = Calculate(op, c1.GetIntConst(), c2.GetIntConst());
        if (np==null) return expr;
        // return an constant
        ConstAst nexpr = new ConstAst(String.valueOf(np));
        nexpr.InsertMeBefore(expr);
        expr.DetachMe();
        return nexpr;
    }
    
    static Integer Calculate(String opnm, int i, int j) {
        if (opnm.compareTo("+")==0) 
            return new Integer(i+j);
        if (opnm.compareTo("-")==0) 
            return new Integer(i-j);
        if (opnm.compareTo("*")==0) 
            return new Integer(i*j);
        if (opnm.compareTo("/")==0) 
            return new Integer(i/j);
        return null;
    }

    // Return the simplified node.  Return null if the node is deleted.
    static public AstNode Simplify(AstNode code) throws AssertionError {
        if (code instanceof AssignStat) {
            // Remove statements like "a=a"
            AssignStat as = (AssignStat) code;
            Addressable lft = as.GetLeftHandOp();
            AstNode nn = Evaluate((AstNode) as.GetRightHandOp());
            if ((nn instanceof Addressable) && lft.equals((Addressable) nn)) {
                    code.DetachMe();
                    return null;
            }
        }
        if (code instanceof ExprAst) {
            // Remove (1*A), (A*1), (A/1)
            AstNode nn = Evaluate(code);
            if (nn instanceof ExprAst 
                && ((ExprAst)nn).GetNumOperands()==2) {
                ExprAst n1 = (ExprAst) nn;
                boolean leftOk = false, rightOk = false;
                if (n1.GetOperand(0) instanceof ConstAst &&
                    ((ConstAst)n1.GetOperand(0)).IsIntConst() &&
                    ((ConstAst)n1.GetOperand(0)).GetIntConst()==1) 
                    leftOk = true;
                if (n1.GetOperand(1) instanceof ConstAst &&
                    ((ConstAst)n1.GetOperand(1)).IsIntConst() &&
                    ((ConstAst)n1.GetOperand(1)).GetIntConst()==1) 
                    rightOk = true;
                if (n1.GetOperator().GetOpName().compareTo("*")==0) {
                    if (leftOk) {
                        AstNode newd = (AstNode) n1.GetOperand(1);
                        newd.MoveMeBefore(n1);
                        n1.DetachMe();
                        Simplify(newd);
                        return newd;
                    }
                    if (rightOk) {
                        AstNode newd = (AstNode) n1.GetOperand(0);
                        newd.MoveMeBefore(n1);
                        n1.DetachMe();
                        Simplify(newd);
                        return newd;
                    }
                }
                if (n1.GetOperator().GetOpName().compareTo("/")==0) {
                    if (rightOk) {
                        AstNode newd = (AstNode) n1.GetOperand(0);
                        newd.MoveMeBefore(n1);
                        n1.DetachMe();
                        Simplify(newd);
                        return newd;
                    }
                }
            }  
        }// end OperatorAst
        
        // Remove consecutive (& (*))s.
        if (code instanceof UopAcc) {
            AstNode base = (AstNode) ((UopAcc)code).GetBase();
            if (base instanceof UopAcc) {
                if (code.GetNodeName().compareTo(base.GetNodeName())!=0) {
                    // Must be one & and one *
                    AstNode newnode = (AstNode) ((UopAcc)base).GetBase();
                    newnode.MoveMeBefore(code);
                    code.DetachMe();
                    code = newnode;
                }
            }
        }
        
        // Simplify children
        Enumeration chds = code.GetChildren();
        while (chds.hasMoreElements()) {
            AstNode chd = (AstNode) chds.nextElement();
            Simplify(chd);
        }
        return code;
    }
}