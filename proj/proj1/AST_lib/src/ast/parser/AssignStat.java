package ast.parser;

import ast.scanner.OldScanner;

import java.io.IOException;

/* (AssignAst "=" (DataAccAst) (Calculable)) */

public class AssignStat extends StatAst {
    public AssignStat(String nm) {
        super(nm);
    }

    // Addressable leftHandOp;
    // Calculable rightHandOp;
    public Addressable GetLeftHandOp() {
        return (Addressable) GetChild(0);
    }

    public Calculable GetRightHandOp() {
        return (Calculable) GetChild(1);
    }

    void ReadProgram(OldScanner scanner)
            throws IOException, AssertionError {
        Addressable leftHandOp = (Addressable) ReadNextAstNode(scanner);
        ((AstNode) leftHandOp).ReadProgram(scanner);
        Calculable rightHandOp = (Calculable) ReadNextAstNode(scanner);
        ((AstNode) rightHandOp).ReadProgram(scanner);
        assert scanner.MatchSym(")");
        AddChild((AstNode) leftHandOp);
        AddChild((AstNode) rightHandOp);
    }

    public String DumpAdap() throws AssertionError {
        String str = "(" + AssignStatLabel + " =";
        str += GetLeftHandOp().DumpAdap();
        str += " ";
        str += GetRightHandOp().DumpAdap();
        str += ")\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        String str = GetLeftHandOp().DumpC();
        str += " = ";
        str += GetRightHandOp().DumpC();
        str += ";\n";
        return str;
    }
}
