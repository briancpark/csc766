/*
 * CodeBlock.java
 *
 * Created on October 23, 2001, 10:21 AM
 */

package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.StringToken;

import java.io.IOException;
import java.util.Enumeration;

/**
 * @author administrator
 * @version The equvalent of code block in C language.
 */

public class CodeBlock extends SegAst {
    public CodeBlock(String fnm) {
        super(fnm);
    }

    public AstNode GetFirstExecutableStatement() {
        Enumeration chds = GetChildren();
        while (chds.hasMoreElements()) {
            AstNode chd = (AstNode) chds.nextElement();
            if (chd instanceof Calculable || chd instanceof Addressable
                    || chd instanceof StatAst)
                return chd;
            if (chd instanceof CodeBlock) {
                AstNode nd = ((CodeBlock) chd).GetFirstExecutableStatement();
                if (nd != null) return nd;
            }
        }
        return null;
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        // Read code block body
        assert scanner.MatchID("Begin");
        assert scanner.MatchSym(")");

        AstNode nn;
        for (nn = ReadNextAstNode(scanner);
             true;
             nn = ReadNextAstNode(scanner)) {
            assert !(nn instanceof FileAst) &&
                    !(nn instanceof ProgAst) &&
                    !(nn instanceof FuncAst);
            if (nn instanceof CodeBlock && ((StringToken) scanner.
                    PreviewNextToken()).GetString().compareTo("End") == 0)
                break;
            nn.ReadProgram(scanner);
            AddChild(nn);
        }

        // End of function (nn instanceof FuncAst)
        //nt = scanner.GetNextToken();
        assert scanner.MatchID("End");
        // delete nn, nt;
        assert scanner.MatchSym(")");
    }

    public String DumpC() throws AssertionError {
    /* If the symbol and type tables are empty, then we don't need to generate
       enclosing brackets.
       We also need to check whether this is the body of a function. 
    */
        String str = "";
        boolean skipBrackets = !NeedBrackets();
        String bname = null;
        if (GetParentAst() instanceof FuncAst)
            bname = "body of func " + GetParentAst().GetNodeName();
        if (!skipBrackets) str += "{\n";
        else str += "\n";
        for (int i = 0; i < GetNumChildren(); i++) {
            AstNode cd = GetChild(i);
            if (cd instanceof LabelStat) str += "   ";
            else str += "      ";
            str += cd.DumpC();
        }
        if (!skipBrackets) {
            if (GetParentAst() instanceof CodeBlock)
                str += "      }\n";
            else str += "}\n";
        }
        return str;
    }
}
