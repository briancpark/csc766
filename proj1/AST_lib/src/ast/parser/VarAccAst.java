package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/** Representing the access to a variable or a field name.
    (VarAccAst var_name)
**/

public class VarAccAst extends AstNode implements Addressable {
    private VarAst var;
    
    /** Can be invoked only when the VarAccAst is properly linked to
     *   a code block with symbol table hierarchy.
     */
    public VarAst GetVar() throws AssertionError {
        if (var==null) {
            // Search symbol table to find the VarAst
            var = SearchSymbolTable(nodeName);
            if (var==null) {
                System.err.println("Symbol '" + nodeName + "' not found.");
                throw new AssertionError();
            }
            else return var;
        }
        else return var;
    }
    
    public VarAccAst(String vname) { super(vname); }
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        // Get symbol table entry on demand
        // VarAst tst = GetVar();  
        assert (scanner.MatchSym(")"));
    }
    
    public String DumpAdap() throws AssertionError {
        return " ( " + VarAccAstLabel + " " + nodeName + ")";
    }
    
    public String DumpC() throws AssertionError {
        return GetVar().GetNodeName();
    }
    
    public Object clone() throws CloneNotSupportedException {
        VarAccAst nt = (VarAccAst) super.clone();
        nt.var = null;  // needs to be reinitiated in a new environment
        return nt;
    }
    
    void UnwireMe() throws AssertionError {
        var = null;
    }
    
    public void CheckConsistency() throws AssertionError, IOException {
        VarAst cur = SearchSymbolTable(nodeName);
        if (cur == null && !IsFuncCall()) 
                Error("Undefined variable name "+GetNodeName());        
        else if (var!=null && var!=cur) 
            Error("Inconsistent variable name "+GetNodeName());
        else if (var == null) 
            var = cur;
        super.CheckConsistency();
    }        
    
    public boolean equals(Addressable a2) throws AssertionError {
        if (!(a2 instanceof VarAccAst)) return false;
        return (GetVar() == ((VarAccAst)a2).GetVar());
    }    
    
    public boolean IsFuncCall() throws AssertionError {
        // Can be an implicitly declared function
        if ((GetParentAst() instanceof ExprAst) && 
            (((ExprAst)GetParentAst()).IsFuncCall())) 
                if (GetMyChildNum()==0) return true;
        return false;
    }
        
}