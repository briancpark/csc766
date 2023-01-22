/*
 * ClassName.java
 *
 * Created on October 3, 2001, 10:51 AM
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

/* (ReturnStat return Calculable) */

public class ReturnStat extends StatAst {
    // Calculable retVal;
    public Calculable GetReturnValue() {return (Calculable) GetChild(0);}
    public boolean HasReturnValue() {return GetNumChildren()!=0;}
    
    public ReturnStat(String nm) {super(nm);}
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        StringToken nt = (StringToken) scanner.PreviewNextToken();
        if (nt.GetString().compareTo(")")!=0) {
            Calculable retVal = (Calculable) ReadNextAstNode(scanner);
            ((AstNode) retVal).ReadProgram(scanner);
            AddChild((AstNode) retVal);
        }
        scanner.MatchSym(")");
    }

    public String DumpAdap() throws AssertionError {
        String str = "(" + ReturnStatLabel + " " + nodeName + " ";
        if (HasReturnValue()) str += GetReturnValue().DumpAdap();
        str += ")\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        String str = "return ";
        if (HasReturnValue()) str += GetReturnValue().DumpC();
        str += ";\n";
        return str;
    }
}
