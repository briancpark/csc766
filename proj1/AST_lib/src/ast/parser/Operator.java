package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;

/* Operator used in an expression.  Currently just a string */

public class Operator extends Object implements Cloneable {
    String opname;
    public Operator(String nm) { opname=nm;}
    public String GetOpName() {return opname;}
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        assert (false);
    }

    public String DumpAdap() {return " "+opname+" ";}
   
    public String DumpC() {return DumpAdap();}
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}