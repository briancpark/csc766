package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.StringToken;
import ast.scanner.Token;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

// (StructType s_name (VarAst ...)*)

public class StructType extends BaseType {
    // See explanation for ArrayType.declaredNames.
    static Hashtable declaredNames;
    boolean incomplete;  // Whether it is a pre-declaration

    StructType(String snm) {
        super(snm);
        incomplete = true;
    }

    public Enumeration GetFields() throws AssertionError {
        assert (!incomplete);
        return GetChildren();
    }

    public int GetNumFields() throws AssertionError {
        assert (!incomplete);
        return GetNumChildren();
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        Token nt = scanner.PreviewNextToken();
        assert (nt instanceof StringToken);
        while (((StringToken) nt).GetString().compareTo(")") != 0) {
            // Read structure fields
            FieldType fld = (FieldType) ReadNextAstNode(scanner);
            fld.ReadProgram(scanner);
            AddChild(fld);
            nt = scanner.PreviewNextToken();
            assert (nt instanceof StringToken);
        }
        nt = scanner.GetNextToken();
        incomplete = false;
    }

    public String DumpAdap() throws AssertionError {
        assert (!incomplete);
        String str = " ( " + StructTypeLabel + " " + nodeName + " ";
        for (int i = 0; i < GetNumChildren(); i++)
            str += ((FieldType) GetChild(i)).DumpAdap();
        str += " )\n";
        return str;
    }

    String GetPreDeclarations() throws AssertionError {
        // Predeclare a few things:
        //   o everything needed to declare a function field
        //   o any array or function pointer as the element type of an array variable
        //   o any array pointer, e.g. a pointer to int[16]

        String str = new String();
        for (int f = 0; f < GetNumChildren(); f++) {
            FieldType ft = (FieldType) GetChild(f);
            if (ft.GetFieldType().GetBaseType() instanceof FuncType) {
                str += ((FuncType) ft.GetFieldType().GetBaseType()).GetPreDeclarations();
            } else if (ft.GetFieldType().GetBaseType() instanceof ArrayType) {
                ArrayType at = (ArrayType) ft.GetFieldType().GetBaseType();
                DataType elemType = at.GetElemType();
                if (elemType.IsArrayType() || elemType.IsFuncType()) {
                    String afdecl = elemType.GetTypeDecl();
                    if (afdecl.lastIndexOf(';') != -1)
                        str += afdecl.substring(0, afdecl.lastIndexOf(';') + 1) + "\n";
                } else if (ft.GetFieldType().IsArrayType()) {
                    String afdecl = ft.GetFieldType().GetTypeDecl();
                    if (afdecl.lastIndexOf(';') != -1)
                        str += afdecl.substring(0, afdecl.lastIndexOf(';') + 1) + "\n";
                }
            }
        }
        return str;
    }

    public String DumpC() throws AssertionError {
        assert (!incomplete);

        String str = GetPreDeclarations();

        // Structure definition starts here.
        str += "\nstruct " + nodeName + " {\n";
        for (int i = 0; i < GetNumChildren(); i++) {
            FieldType ft = (FieldType) GetChild(i);
            if (!(ft.GetFieldType().GetBaseType() instanceof FuncType)) {
                str += "\t" + ft.DumpC();
                str += ";\n";
            } else {
                FuncType fct = (FuncType) ft.GetFieldType().GetBaseType();
                str += "\t" + fct.GetRetType().GetTypeDecl();
                int indir = ft.GetFieldType().GetIndir();
                assert (indir > 0);
                str += " (";
                for (int t = 0; t < indir; t++)
                    str += "*";
                str += ft.GetNodeName() + ") (";  // The default is one-level pointer
                for (int p = 0; p < fct.GetNumParams(); p++) {
                    VarAst par = fct.GetParam(p);
                    if (!(par.GetVarType().GetBaseType() instanceof StructType))
                        str += par.ParameterDumpC();
                    else {
                        DataType st = (DataType) par.GetVarType();
                        String sdecl = st.GetTypeDecl();
                        str += tools.StringTools.ConvertToIdentifier(sdecl) + " ";
                    }
                    if (p < fct.GetNumParams() - 1) str += ",";
                }
                str += ");\n";
            } // end function field decl
        } // end all fields

        str += "};\n";
        return str;
    }

    public FieldType SearchField(String fieldNm) throws AssertionError {
        assert (!incomplete);
        for (int i = 0; i < GetNumChildren(); i++)
            if (((FieldType) GetChild(i)).GetNodeName().compareTo(fieldNm)
                    == 0)
                return (FieldType) GetChild(i);
        return null;
    }

    /**
     * Special coying, only used in constructing types when parsing a
     * program in the intermediate format. Type src is destructed.
     * void TransferStructTypeFrom(StructType src) throws AssertionError {
     * assert (incomplete);
     * incomplete = false;
     * for (int i=0; i<src.GetNumChildren(); i++)
     * src.GetChild(i).MoveMeAsChildOf(this);
     * }
     */

    public Object clone() throws CloneNotSupportedException {
        if (incomplete)
            throw new CloneNotSupportedException();
        return super.clone();
    }
}