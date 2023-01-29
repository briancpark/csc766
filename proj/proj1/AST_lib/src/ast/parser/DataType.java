package ast.parser;

import ast.scanner.*;

import java.io.IOException;
import java.io.PrintStream;

// ?? Where do I put the ArrayType node in AST tree?  Under function or under here?


/**
 * (DataType base_type_name indir),
 * (DataType array (ArrayType) indir), or
 * (DataType function (FuncType) indir).
 * <p>
 * Either a base type (primitive or structure) or a pointer to a base type
 * indir is 0 if it is not a pointer
 * <p>
 * Obsolete: If (IsArrayType()) then baseType is the element type of the array.
 * <p>
 * Two public functions are used in the place of GenCode:
 * GenTypeDecl() generates the type declaration
 * GenDataDecl(String data_name) generates the data declaration
 */


public class DataType extends AstNode {
    int indir;
    private BaseType baseType;
    private DataType contentType, pointerType;  // cache a copy instead of recomputing

    DataType(String nm) {
        super(nm);
    }

    /* parent field should always be set when the parent
     *  of this DataType is inquired */
    public DataType(String typnm, int ind) throws AssertionError {
        super(typnm);
        indir = ind;
    }

    DataType(BaseType btyp, int ind) {
        // Dangling data type that's not linked in the AstTree
        //  For constructing content and pointer types.
        super(btyp.GetNodeName());
        baseType = btyp;
        indir = ind;
    }

    public BaseType GetBaseType() throws AssertionError {
        if (IsArrayType() || IsFuncType()) {
            if (GetNumChildren() == 0) {
                // If this is constructed DataType
                assert (baseType != null);
                return baseType;
            } else return (BaseType) GetChild(0);
        } else if (baseType == null) {
            baseType = SearchTypeTable(GetNodeName());
            if (baseType == null) {
                Error("Type name '" + GetNodeName() + "' not found.\n");
                return null;
            }
        }
        return baseType;
    }

    public int GetIndir() throws AssertionError {
        assert (indir >= 0);
        return indir;
    }

    public boolean IsArrayType() throws AssertionError {
        if (GetNodeName().compareTo("array") == 0)
            return true;
        else return false;
    }

    public boolean IsFuncType() throws AssertionError {
        if (GetNodeName().compareTo("function") == 0)
            return true;
        else return false;
    }

    public boolean IsPointerType() {
        return (indir > 0);
    }

    public int GetNumArrayDim() throws AssertionError {
        // a pointer var is an array with one dimension, regardless
        //   the value of indir.
        // a pointer to an array is counted as array dim plus 1
        int bdim;
        BaseType bt = GetBaseType();
        if (IsArrayType())
            bdim = ((ArrayType) bt).GetDimensions().size();
        else bdim = 0;
        return bdim + indir;
    }

    // For pointers, it is one less indirection. 
    // For an array, it is the element type.
    // Undefined on primitive, struct, and function types
    public DataType GetContentType() throws AssertionError {
        if (contentType != null) return contentType;
        if (indir == 0) {
            if (GetBaseType() instanceof ArrayType) {
                contentType = new DataType(
                        ((ArrayType) GetBaseType()).GetElemType().GetBaseType(),
                        ((ArrayType) GetBaseType()).GetElemType().GetIndir());
            } else throw new AssertionError();
        } else contentType = new DataType(GetBaseType(), indir - 1);
        return contentType;
    }

    public DataType GetPointerType() throws AssertionError {
        if (pointerType != null) return pointerType;
        pointerType = new DataType(GetBaseType(), indir + 1);
        return pointerType;
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        if (!nodeName.equals("array") && !nodeName.equals("function")) {
            // A primitive type can be more than one strings, e.g. unsigned char
            Token nt = scanner.PreviewNextToken();
            while (nt instanceof IDToken) {
                nodeName = nodeName + " " + ((StringToken) nt).GetString();
                scanner.GetNextToken();
                nt = scanner.PreviewNextToken();
            }
            // on-demand later: 
            // baseType = SearchTypeTable(nodeName);
            nt = scanner.GetNextToken();
            assert (nt instanceof NumToken);
            indir = Integer.parseInt(((NumToken) nt).GetString());
        } else {
            // an array type or a function variable
            BaseType baseType = (BaseType) ReadNextAstNode(scanner);
            baseType.ReadProgram(scanner);
            AddChild(baseType);
            Token nt = scanner.GetNextToken();
            assert (nt instanceof NumToken);
            indir = Integer.parseInt(((NumToken) nt).GetString());
        }

        assert (scanner.MatchSym(")"));
    }

    public String DumpAdap() throws AssertionError {
        String str = " ( " + DataTypeLabel + " " + nodeName + " ";
        if (IsArrayType() || IsFuncType())
            str += GetBaseType().DumpAdap();
        str += " " + indir + " ) ";
        return str;
    }

    public String DumpC() throws AssertionError {
        assert (false); // Wrong entry
        return null;
    }

    public void GenTypeDecl(PrintStream fout) throws IOException, AssertionError {
        fout.print(GetTypeDecl());
    }

    public String GetTypeDecl() throws AssertionError {
        // Cannot declare function type as a declaration
        // Cannot declare array type as a declaration
        String typdcl;
        if (GetBaseType() instanceof FuncType)
            typdcl = ((FuncType) GetBaseType()).GetTypeDecl();
        else if (GetBaseType() instanceof ArrayType)
            typdcl = ((ArrayType) GetBaseType()).GetTypeDecl();
        else {
            if (GetBaseType() instanceof StructType)
                typdcl = "struct ";
            else typdcl = "";
            typdcl += GetBaseType().GetNodeName();
        }
        for (byte i = 0; i < indir; i++)
            typdcl += "*";
        return typdcl;
    }

    public String GetTypeDescription() throws AssertionError {
        if (GetBaseType() instanceof ArrayType)
            return ((ArrayType) GetBaseType()).GetDataDecl("dummy", indir);
        if (GetBaseType() instanceof FuncType)
            return ((FuncType) GetBaseType()).GetDataDecl("dummy", indir);
        else return GetTypeDecl();
    }

    public String GetDataDecl(String dnm) throws AssertionError {
        return GetBaseType().GetDataDecl(dnm, indir);
    }

    public void GenDataDecl(PrintStream fout, String dnm)
            throws IOException, AssertionError {
        GetBaseType().GenDataDecl(fout, dnm, indir);
    }

    public void CheckConsistency() throws AssertionError, IOException {
        if (!IsArrayType() && !IsFuncType()) {
            BaseType curType = SearchTypeTable(nodeName);
            if (curType == null) {
                // Special rule here: a pointer can point to an 
                //  undefined structure.  We just initialize it 
                //  to be a structure with no inner fields.  If 
                //  the internal of the structure is accessed, then
                //  it will signal an error.
                StructType newstruct = new StructType(nodeName);
                GetInnermostScope().typeTable.put(nodeName, newstruct);
                Warning("Undefined type name '" + GetNodeName() + "'.  No internal field can be accessed.");
            }
            if (baseType != null && baseType != curType)
                Error("Inconsistent type name '" + GetNodeName() + "'");
            else if (baseType == null)
                baseType = curType;
        }
        super.CheckConsistency();
    }

    void UnwireMe() throws AssertionError {
        baseType = null;
    }

    public Object clone() throws CloneNotSupportedException {
        DataType nt = (DataType) super.clone();
        nt.baseType = null;
        return nt;
    }
}    

    
    