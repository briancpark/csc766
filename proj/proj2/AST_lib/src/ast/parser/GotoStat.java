package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.StringToken;

import java.io.IOException;

/* (GotoStat label_name (OperatorAst)) */

public class GotoStat extends StatAst {
    public GotoStat(String nm) {
        super(nm);
    }

    // Calculable cond;
    public Calculable GetCondition() {
        return (Calculable) GetChild(0);
    }

    boolean IsConditionalGoto() {
        return GetNumChildren() != 0;
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        StringToken nt = (StringToken) scanner.PreviewNextToken();
        if (nt.GetString().compareTo(")") != 0) {
            Calculable cond = (Calculable) ReadNextAstNode(scanner);
            ((AstNode) cond).ReadProgram(scanner);
            AddChild((AstNode) cond);
        }
        scanner.MatchSym(")");
    }

    public String DumpAdap() throws AssertionError {
        String str = "(" + GotoStatLabel + " " + nodeName;
        if (IsConditionalGoto()) str += GetCondition().DumpAdap();
        str += ")\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        String str = "";
        if (IsConditionalGoto()) {
            str += "if (";
            str += GetCondition().DumpC();
            str += ") ";
        }
        str += "goto ";
        str += nodeName + ";\n";
        return str;
    }
}
