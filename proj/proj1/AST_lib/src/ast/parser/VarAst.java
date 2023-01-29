package ast.parser;

import ast.scanner.OldScanner;
import tools.StringTools;

import java.io.IOException;

/**
 * (VarAst var_name DataType)
 * Representing a variable definition, not its reference (which is VarAccAst).
 * Representing a function or an array variable when var_type is FuncType
 * or ArrayType.
 * <p>
 * Each instance of VarAst must properly belong to a symbol table.
 **/

public class VarAst extends AstNode implements Declarative {
    VarAst(String vnm) {
        super(vnm);
    }

    public VarAst(String vnm, DataType dt)
            throws AssertionError {
        super(vnm);
        AddChild(dt);
    }

    // DataType varType;
    // Comment location, initialization;
    public DataType GetVarType() throws AssertionError {
        return (DataType) GetChild(0);
    }

    public Comment GetLocation() {
        return (Comment) GetAttribute("location");
    }

    public Comment GetInitialization() {
        return (Comment) GetAttribute("initialization");
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        DataType varType = (DataType) ReadNextAstNode(scanner);
        varType.ReadProgram(scanner);
        AddChild(varType);

        //Symbol table insertion happens at SegAst level, not here.
        // VarAst can be function parameters.
        //AstNode par = GetParentAst();
        //RootedTable symbolTable = ((SegAst)par).GetSymbolTable();
        //assert (!symbolTable.containsKey(nodeName));
        //symbolTable.put(nodeName, varType);

        assert (scanner.MatchSym(")"));
    }

    public String DumpAdap() throws AssertionError {
        String str = " ( " + VarAstLabel + " " + nodeName + " ";
        str += GetVarType().DumpAdap();
        str += " )";
        return str;
    }

    public String ParameterDumpC() throws AssertionError {
        return GetVarType().GetDataDecl(nodeName);
    }

    public String DumpC() throws AssertionError {
        String str = GetVarType().GetDataDecl(nodeName);
        if (GetInitialization() != null) {
            String init = GetInitialization().GetComment();
            init = StringTools.StripQuotationMarks(init);
            str += "= " + init;
        }
        str += ";\n";
        return str;
    }

    /**
     * Clone is not supported because it's unclear what to do with symbol tables
     */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}