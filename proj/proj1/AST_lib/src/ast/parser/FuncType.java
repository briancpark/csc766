package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.SymToken;
import ast.scanner.Token;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;

// (FuncType func_name (DataType) (VarAst)*), 
//  func_name is irrelevant here.
//  the first param is the return type and the rest are arguments
//  
public class FuncType extends BaseType {
    /* See explanation for ArrayType.declaredNames */
    static Hashtable declaredNames;

    FuncType(String nm) {
        super(nm);
    }

    public FuncType(DataType ret) throws AssertionError {
        super("functype");
        AddChild(ret);
    }

    public DataType GetRetType() {
        return (DataType) GetChild(0);
    }

    public int GetNumParams() {
        return GetNumChildren() - 1;
    }

    public VarAst GetParam(int i) {
        return (VarAst) GetChild(i + 1);
    }

    public void AddParam(VarAst newpara) throws AssertionError {
        assert (GetNumChildren() >= 1);
        AddChild(newpara);
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        DataType retType = (DataType) ReadNextAstNode(scanner);
        retType.ReadProgram(scanner);
        AddChild(retType);
        Token nt = scanner.PreviewNextToken();
        while (((SymToken) nt).GetString().compareTo("(") == 0) {
            VarAst par = (VarAst) ReadNextAstNode(scanner);
            par.ReadProgram(scanner);
            AddChild(par);
            nt = scanner.PreviewNextToken();
        }
        assert (scanner.MatchSym(")"));
    }

    public String DumpAdap() throws AssertionError {
        String str = " ( " + FuncTypeLabel + " " + nodeName + " ";
        str += GetRetType().DumpAdap();
        for (int i = 1; i < GetNumChildren(); i++)
            str += GetChild(i).DumpAdap();
        str += " )\n";
        return str;
    }

    public void GenCode(PrintStream fout) throws IOException, AssertionError {
        assert (false);  // do not gen code here.
    }

    /** fnm is the name of the variable/field of this type.  indir is the
     level of indirection to this function.
     **/

    String GetPreDeclarations() throws AssertionError {
        // Predeclare in the following cases:
        //  o A function returning a pointer to an array
        //  o A parameter is of structure type (the structure may be defined later)
        //  o A parameter is a pointer to an array or a function

        String str = "";
        if (GetRetType().IsArrayType() || GetRetType().IsFuncType()) {
            String tmp = GetRetType().GetTypeDecl();
            if (tmp.lastIndexOf(';') != -1)
                str += tmp.substring(0, tmp.lastIndexOf(';') + 1) + "\n";
        }
        for (int p = 0; p < GetNumParams(); p++) {
            VarAst par = GetParam(p);
            if (par.GetVarType().GetBaseType() instanceof StructType) {
                DataType st = par.GetVarType();
                String sdecl = st.GetTypeDecl();
                String declareName = tools.StringTools.ConvertToIdentifier(sdecl);
                if (!StructType.declaredNames.containsKey(declareName)) {
                    str += "typedef " + sdecl + " ";
                    str += declareName + ";\n";
                    StructType.declaredNames.put(declareName, declareName);
                }
            } else if (par.GetVarType().IsArrayType() || par.GetVarType().IsFuncType()) {
                String tmp = par.GetVarType().GetTypeDecl();
                if (tmp.lastIndexOf(';') != -1)
                    str += tmp.substring(0, tmp.lastIndexOf(';') + 1) + "\n";
            }
        }
        return str;
    }

    /**
     * If this is the type of a function variable.
     */
    public String GetDataDecl(String fnm, int indir) throws AssertionError {
        // Pre-declare any typedefs in parameters
        String str = GetPreDeclarations();

        // Generate function declaration
        str += GetRetType().GetTypeDecl();
        if (indir > 0) {
            str += " (";
            for (byte i = 0; i < indir; i++)
                str += "*";
            str += fnm + ") (";  // The default is one-level pointer
        } else str += " " + fnm + "(";
        for (int i = 1; i < GetNumChildren(); i++) {
            str += ((VarAst) GetChild(i)).ParameterDumpC();
            if (i != GetNumChildren() - 1)
                str += ", ";
        }
        str += ") ";
        return str;
    }

    /**
     * If this is the type of a function definition
     */
    public String GetFuncDecl(String fnm) throws AssertionError {
        return GetDataDecl(fnm, 0);
    }

    String GetTypeDecl() throws AssertionError {
        String fnm = tools.StringTools.ConvertToIdentifier(GetFuncDecl("f"));
        if (declaredNames.containsKey(fnm))
            return fnm;
        else {
            String dcl = "typedef " + GetDataDecl(fnm, 0) + ";\n";
            declaredNames.put(fnm, fnm);
            return dcl + fnm;
        }
    }
}    
