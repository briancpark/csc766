package ast.parser;

import ast.scanner.OldScanner;

import java.io.IOException;

/* (LabelStat label_name) */

public class LabelStat extends StatAst {
    public LabelStat(String nm) {
        super(nm);
    }

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