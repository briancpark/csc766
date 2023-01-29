/*
 * ConstAst.java
 *
 * Created on September 24, 2001, 8:05 PM
 */

package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author  administrator
 * @version 
 */
/* Scalar constants are supposedly not addressable, but string constants are */
public class ConstAst extends AstNode implements Addressable {
    Integer constInt;
       
    /** Creates new ConstAst, Integer constant is treated specially because of
        it use in address calculation. Otherwise, we don't care which constant
        it is. */
    public ConstAst(String nm) throws AssertionError {
        super(nm); ExamineConst(nm);
    }
    
    void ExamineConst(String nm) {
        String t;
        if (nm.startsWith("- "))
            t = "-"+nm.substring(2);
        else t = nm;

         try {
             constInt = new Integer(t);
         } catch (NumberFormatException e) {
             // not an integer
             constInt = null;
         }
     }
    
     void ReadProgram(OldScanner scanner) throws IOException, AssertionError 
{
         StringToken nt = (StringToken) scanner.GetNextToken();
         StringBuffer nb = new StringBuffer();
         nb.append(nodeName);
         while (nt.GetString().compareTo(")")!=0) {
             nb.append(" "+ nt.GetString());
             nt = (StringToken) scanner.GetNextToken();
         }
         nodeName = nb.toString();
         ExamineConst(nodeName);
         return;
     }
    
    public boolean IsIntConst() {
        return (constInt != null);
    }
    public int GetIntConst() throws AssertionError {
        return constInt.intValue();}
    
    public String DumpAdap() {
        return "("+ ConstAstLabel+" "+nodeName+")";
    }
    
    public String DumpC() { return nodeName;}
    
    public boolean equals(Addressable a2) throws AssertionError {
        if (!(a2 instanceof ConstAst )) return false;
        ConstAst aa = (ConstAst) a2;
        if (!(IsIntConst() && aa.IsIntConst())) return false;
        return (GetIntConst()==aa.GetIntConst());
    }
}
