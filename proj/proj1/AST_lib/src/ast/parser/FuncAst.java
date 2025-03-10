package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.StringToken;
import ast.scanner.SymToken;
import ast.scanner.Token;

import java.io.IOException;

/**
 * (FuncAst fname storage_class (FuncType ...) [Begin])
 * (FuncAst fname End)
 * storage_class can be AUTO, EXTERN, or STATIC
 * Function defintion, or func declaration if Begin and End are not there.
 * Function variables are expressed as VarAst.
 * If GetFuncBody returns null, then it is a function declaration.
 * <p>
 * To do: add func def into the file's symbol table
 */

public class FuncAst extends AstNode {
    //FuncType funcType;
    //CodeBlock funcBody;
    StorageClass sclass;

    FuncAst(String fnm) {
        super(fnm);
    }

    public FuncAst(String fnm, StorageClass sc, FuncType ftype, CodeBlock fbody)
            throws AssertionError {
        super(fnm);
        sclass = sc;
        AddChild(ftype);
        AddChild(fbody);

        // Insert the parameters into the symbol table
        for (int p = 0; p < ftype.GetNumParams(); p++) {
            VarAst f = ftype.GetParam(p);
            fbody.GetSymbolTable().put(f.GetNodeName(), f);
        }
    }

    public FuncType GetFuncType() {
        return (FuncType) GetChild(0);
    }

    public CodeBlock GetFuncBody() {
        return (CodeBlock) GetChild(1);
    }

    public StorageClass GetSClass() {
        return sclass;
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        sclass = StorageClass.GetStorageClass(
                ((StringToken) scanner.GetNextToken()).GetString());

        // Read function return type and parameters
        FuncType funcType = (FuncType) ReadNextAstNode(scanner);
        funcType.ReadProgram(scanner);
        AddChild(funcType);

        // If it is a function declaration (no "Begin")
        Token nt = scanner.PreviewNextToken();
        if (nt instanceof SymToken) {
            assert (scanner.MatchSym(")"));
            return;
        }

        // A function, insert the parameters into the symbol table
        // Delay it until funcBody node begins.

        // Read function body
        assert (scanner.MatchID("Begin"));
        assert (scanner.MatchSym(")"));

        CodeBlock nn;
        nn = (CodeBlock) ReadNextAstNode(scanner);

        // Insert the parameters into the symbol table
        FuncType ft = GetFuncType();
        for (int p = 0; p < ft.GetNumParams(); p++) {
            VarAst f = ft.GetParam(p);
            nn.GetSymbolTable().put(f.GetNodeName(), f);
        }

        // Read the rest of the function body
        nn.ReadProgram(scanner);
        AddChild(nn);

        FuncAst fend;
        fend = (FuncAst) ReadNextAstNode(scanner);
        assert (scanner.MatchID("End"));
        assert (scanner.MatchSym(")"));
        AddChild(fend);
    }

    public String DumpAdap() throws AssertionError {
        String str = "( " + FuncAstLabel + " " + nodeName + " " + sclass.GetName() + " ";
        str += GetFuncType().DumpAdap();
        str += " Begin )";
        str += GetFuncBody().DumpAdap();
        str += "( " + FuncAstLabel + " " + nodeName + " End )";
        return str;
    }

    public String DumpC() throws AssertionError {
        String str = "\n";
        if (sclass.GetName().compareTo("") != 0)
            str += sclass.GetName() + " ";
        str += GetFuncType().GetFuncDecl(nodeName);
        str += GetFuncBody().DumpC();
        return str;
    }
}
