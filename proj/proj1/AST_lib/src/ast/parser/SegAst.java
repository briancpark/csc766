package ast.parser;

import tools.StringTools;

import java.util.Enumeration;
import java.util.Hashtable;

// SegNode: code segement that contains any sequence of AstNode
//  e.g. a program, a function, a loop, a basic block

abstract public class SegAst extends AstNode {
    Hashtable symbolTable, typeTable;

    public SegAst(String snm) {
        super(snm);
        typeTable = new Hashtable();
        symbolTable = new Hashtable();
    }

    // Vector segNodes;
    public Enumeration GetSegNodes() {
        return GetChildren();
    }

    public Hashtable GetSymbolTable() throws AssertionError {
        assert (symbolTable != null);
        return symbolTable;
    }

    public Hashtable GetTypeTable() throws AssertionError {
        assert (typeTable != null);
        return typeTable;
    }

    public boolean NeedBrackets() throws AssertionError {
        boolean skipBrackets = (symbolTable.isEmpty() && typeTable.isEmpty()
                && !(GetParentAst() instanceof FuncAst))
                || !(this instanceof CodeBlock);
        return !skipBrackets;
    }

    public void AddChild(AstNode nn, int i) throws AssertionError {
        ProcessSegNode(nn, i);
    }

    private void ProcessSegNode(AstNode nn, int pos) throws AssertionError {
        /*** Symbol and type table insertion happens here in the home
         *** of the symbol table. It could happen within these nodes too. ***/
        if (nn instanceof ImportFile) {
            FileAst ifile = (FileAst) ((ImportFile) nn).GetChild(0);
            // header file insertion
            Enumeration chds = ifile.GetChildren();
            while (chds.hasMoreElements()) {
                AstNode chd = (AstNode) chds.nextElement();
                chd.MoveMeAsChildOf(this);
            }
            return;
        }
        if (nn instanceof Comment) {
            // Insert comments as attributes
            // Any item of a SegAst can have comments attached.
            GetChild(pos - 1).InsertAttribute(nn.GetNodeName(), nn);
            return;
        }
        if (nn instanceof VarAst) {
            // One special case is a function declaration
            if (((VarAst) nn).GetVarType().IsFuncType() &&
                    ((VarAst) nn).GetVarType().GetIndir() == 0) {
                if (!symbolTable.containsKey(nn.GetNodeName()))
                    symbolTable.put(nn.GetNodeName(), nn);
                // Do not check for consistency of multiple func decl
                //  Assume it has been done before.
            } else {
                assert (!symbolTable.containsKey(nn.GetNodeName()));
                symbolTable.put(nn.GetNodeName(), nn);
                // Question: do we want to add this to SegNodes?
            }
        }
        if (nn instanceof BaseType) {
            assert (!typeTable.containsKey(nn.GetNodeName()));
            typeTable.put(nn.GetNodeName(), nn);
        }
        if (nn instanceof FuncAst) {
            // A function definition is a VarAst
            // Consistency of definitions are assumed, not checked.  But can 
            //   be checked easily.
            assert (this instanceof FileAst);
            VarAst nt = SearchSymbolTable(nn.GetNodeName());
            /* Disabled: Put function definition as a base type in type table
            if (typeTable.containsKey(nn.GetNodeName()))
                Error("Function name is the same as that of a type or another function.");
            typeTable.put(nn.GetNodeName(), ((FuncAst)nn).GetFuncType());
            */
            if (nt == null) {
                VarAst fvar = new VarAst(nn.GetNodeName(),
                        new DataType(((FuncAst) nn).GetFuncType(), 0));
                symbolTable.put(nn.GetNodeName(), fvar);
            }
        }
        super.AddChild(nn, pos);
    }

    public String DumpAdap() throws AssertionError {
        String classnm = StringTools.TruncatedClassName(getClass().getName());
        String str = "(" + classnm + " " + nodeName + " Begin)\n";
        for (int i = 0; i < GetNumChildren(); i++) {
            str += GetChild(i).DumpAdap();
            Enumeration attrs = GetChild(i).GetAttributes();
            while (attrs.hasMoreElements())
                str += ((Comment) attrs.nextElement()).DumpAdap();
        }
        str += "(" + classnm + " " + nodeName + " End)\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        String classnm = StringTools.TruncatedClassName(getClass().getName());
        String str = "/* " + classnm + " " + nodeName + " Begin */\n";
        String indent = "";
        if (!(GetParentAst() instanceof FileAst || this instanceof FileAst)) indent = "\t";
        for (int i = 0; i < GetNumChildren(); i++) {
            str += indent;
            str += GetChild(i).DumpC();
        }
        str += "/* " + classnm + " " + nodeName + " End */\n";
        return str;
    }

    public Object clone() throws CloneNotSupportedException {
    /* Unusual manuver: let the old hold the new tables so that symbol 
       and type tables will be refilled when cloned children are added */
        Hashtable baks = symbolTable, baky = typeTable;
        symbolTable = new Hashtable();
        typeTable = new Hashtable();
        SegAst nt = (SegAst) super.clone();
        symbolTable = baks;
        typeTable = baky;
        return nt;
    }
}
