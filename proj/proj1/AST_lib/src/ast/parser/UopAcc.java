package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/** Unary operator for data access, i.e. & (taking address) and * (taking content)
 * (UopAcc operator DataAccAst)
 */
public class UopAcc extends DataAccAst {
    public boolean IsDereferencing() {return (nodeName.compareTo("*")==0);}
    public boolean IsTakingAddress() {return !IsDereferencing();}

    UopAcc(String nm) { super(nm); }
        
    public UopAcc(String op, Addressable opd) 
            throws AssertionError {
        super(op); AddBase(opd); CheckOperator(op);
    }
    
    /* must be either * or &
     **/
    void CheckOperator(String op) throws AssertionError {
        assert (op.compareTo("*")==0 || op.compareTo("&")==0);
    }
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        Addressable opd = (Addressable) ReadNextAstNode(scanner);
        ((AstNode) opd).ReadProgram(scanner);
        AddBase(opd);
        assert (scanner.MatchSym(")"));
    }
    
    public String DumpAdap() throws AssertionError {
        String str = " ( " + UopAccLabel + " " + nodeName + " ";
        str += GetBase().DumpAdap();
        str += " )";
        return str;
    }
    
    public String DumpC() throws AssertionError { 
        String str = "("+nodeName;
        str += GetBase().DumpC();
        str += ")";
        return str;
    }
    
    public boolean equals(Addressable a2) throws AssertionError {
        if (!(a2 instanceof UopAcc)) return false;
        UopAcc aa = (UopAcc) a2;
        if (GetNodeName().compareTo(aa.GetNodeName())!=0) return false;
        return GetBase().equals(aa.GetBase());
    }    
}