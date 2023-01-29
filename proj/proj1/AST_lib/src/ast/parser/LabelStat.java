package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/* (LabelStat label_name) */

public class LabelStat extends StatAst {
    public LabelStat(String nm) {super(nm);}
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        scanner.MatchSym(")");
    }

    public String DumpAdap() throws AssertionError {
        String str = "(" + LabelStatLabel + " " + nodeName;
        str += ")\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        return nodeName + ":;\n";
    }
}