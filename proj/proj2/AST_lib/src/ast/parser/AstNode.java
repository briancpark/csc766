package ast.parser;

import ast.scanner.IDToken;
import ast.scanner.OldScanner;
import ast.scanner.StringToken;
import ast.scanner.Token;
import tools.AttributedNode;
import tools.StringTools;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**** Base class: no public constructor because AstNode itself will
 not be physically present in a program.
 ****/

/**** Convention of scanning: With the exception of ProgNode, all
 other node types have the scanner in the following state when
 ReadProgram is called.  The '(' symbol and type name are consumed and
 the node type name is still the current token.  ProgNode needs to
 consume '(' symbol at the beginning.  After ReadProgram, the last ')'
 is consumed.
 ****/

/**** Storage notes: Ast nodes share the storage of StringToken.
 ****/

/**
 * Base class of nodes of abstract syntax tree (AST).
 * It recognizes ADAP intermediate format and outputs either intermediate and
 * C code.
 */

abstract public class AstNode extends AttributedNode implements Cloneable {
    static final String ArrayAccLabel = "ArrayAcc";
    static final String ArrayTypeLabel = "ArrayType";
    //static final String BopAccLabel = "BopAcc";
    //static final String DataAccAstLabel = "DataAccAst";
    static final String DataTypeLabel = "DataType";
    static final String FieldTypeLabel = "FieldType";
    static final String FileAstLabel = "FileAst";
    static final String FuncAstLabel = "FuncAst";
    static final String FuncTypeLabel = "FuncType";
    static final String PrimitiveTypeLabel = "PrimitiveType";
    static final String ProgAstLabel = "ProgAst";
    static final String StructAccLabel = "StructAcc";
    static final String StructTypeLabel = "StructType";
    static final String UopAccLabel = "UopAcc";
    static final String VarAccAstLabel = "VarAccAst";
    static final String VarAstLabel = "VarAst";
    static final String ExprAstLabel = "ExprAst";
    static final String ExprStatLabel = "ExprStat";
    static final String AssignStatLabel = "AssignStat";
    static final String GotoStatLabel = "GotoStat";
    static final String LabelStatLabel = "LabelStat";

    /* Public methods for AST tree manipulation */
    static final String ConstAstLabel = "ConstAst";
    static final String ReturnStatLabel = "ReturnStat";
    static final String CommentLabel = "Comment";
    static final String ImportFileLabel = "ImportFile";
    static final String CodeBlockLabel = "CodeBlock";
    String nodeName;
    AstNode parentAst;
    private Vector children;

    public AstNode(String nn) {
        nodeName = nn;
        children = new Vector();
    }

    static AstNode ReadNextAstNode(OldScanner scanner)
            throws IOException, AssertionError {
        // Read the Ast type label and the name after it
        if (!scanner.HasMoreToken()) return null;
        if (!scanner.MatchSym("(")) {
            StringToken nt = (StringToken) scanner.PreviewNextToken();
            System.err.println("Error: expecting '(' but encountered " + nt.GetString());
            assert false;
            return null;
        }
        Token nt = scanner.GetNextToken();
        assert (nt instanceof IDToken);
        String typ = ((StringToken) nt).GetString();
        nt = scanner.GetNextToken();
        assert (nt instanceof StringToken);
        String tnm = ((StringToken) nt).GetString();
        AstNode newnode;
        String file = scanner.GetFileName();
        int lineno = scanner.GetCurrentLineNo();
        if (typ.compareTo(ArrayAccLabel) == 0) newnode = new ArrayAcc(tnm);
        else if (typ.compareTo(ArrayTypeLabel) == 0) newnode = new ArrayType(tnm);
        else if (typ.compareTo(DataTypeLabel) == 0) newnode = new DataType(tnm);
        else if (typ.compareTo(FieldTypeLabel) == 0) newnode = new FieldType(tnm);
        else if (typ.compareTo(FileAstLabel) == 0) newnode = new FileAst(tnm);
        else if (typ.compareTo(FuncAstLabel) == 0) newnode = new FuncAst(tnm);
        else if (typ.compareTo(FuncTypeLabel) == 0) newnode = new FuncType(tnm);
            /* else if (typ.compareTo(PrimitiveTypeLabel)==0) newnode = new PrimitiveType(tnm); */
        else if (typ.compareTo(StructAccLabel) == 0) newnode = new StructAcc(tnm);
        else if (typ.compareTo(StructTypeLabel) == 0) newnode = new StructType(tnm);
        else if (typ.compareTo(UopAccLabel) == 0) newnode = new UopAcc(tnm);
        else if (typ.compareTo(VarAccAstLabel) == 0) newnode = new VarAccAst(tnm);
        else if (typ.compareTo(VarAstLabel) == 0) newnode = new VarAst(tnm);
        else if (typ.compareTo(ExprAstLabel) == 0) newnode = new ExprAst(tnm);
        else if (typ.compareTo(ExprStatLabel) == 0) newnode = new ExprStat(tnm);
        else if (typ.compareTo(AssignStatLabel) == 0) newnode = new AssignStat(tnm);
        else if (typ.compareTo(GotoStatLabel) == 0) newnode = new GotoStat(tnm);
        else if (typ.compareTo(LabelStatLabel) == 0) newnode = new LabelStat(tnm);
        else if (typ.compareTo(ConstAstLabel) == 0) newnode = new ConstAst(tnm);
        else if (typ.compareTo(ReturnStatLabel) == 0) newnode = new ReturnStat(tnm);
        else if (typ.compareTo(CommentLabel) == 0) newnode = new Comment(tnm);
        else if (typ.compareTo(ImportFileLabel) == 0) newnode = new ImportFile(tnm);
        else if (typ.compareTo(CodeBlockLabel) == 0) newnode = new CodeBlock(tnm);
        else if (typ.compareTo(ProgAstLabel) == 0) {
            // Special operation: return null since this is (ProgAst end)
            return null;
        } else {
            newnode = new Comment("error");
            newnode.Error("Unrecognized AST node: " + typ + " " + tnm
                    + " file " + file + " line " + lineno);
        }
        Comment fc = new Comment("file", file);
        Comment fl = new Comment("line", "" + lineno);
        newnode.InsertAttribute("file", fc);
        newnode.InsertAttribute("line", fl);
        return newnode;
    }

    public AstNode GetParentAst() {
        return parentAst;
    }

    void SetParentAst(AstNode par) {
        parentAst = par;
    }

    /**
     * A copy of the node's current children list.  Later list modifications
     * will not change this list.
     */
    public Enumeration GetChildren() {
        return ((Vector) children.clone()).elements();
    }

    public int GetNumChildren() {
        return children.size();
    }

    AstNode GetChild(int i) {
        return (AstNode) children.elementAt(i);
    }

    /**
     * This is the single entry point for any addition to children list.
     */
    void AddChild(AstNode cd, int i) throws AssertionError {
        if (!(cd.GetParentAst() == null || cd.GetParentAst() == this))
            Error("Illegal tree insertion");
        cd.parentAst = this;
        children.insertElementAt(cd, i);
    }

    void AddChild(AstNode cd) throws AssertionError {
        AddChild(cd, GetNumChildren());
    }

    /**
     * This is the single entry point for any deletion to children list.
     */
    private void DetachChild(int i) {
        children.removeElementAt(i);
    }

    public String GetNodeName() {
        return nodeName;
    }

    /* get the working directory where all input files are */
    public String GetWorkingDirectory() {
        if (this instanceof ProgAst || this instanceof FileAst)
            return ((Comment) this.GetAttribute("WorkingDirectory")).GetNodeName();
        else return this.GetParentAst().GetWorkingDirectory();
    }

    public String DumpC() throws AssertionError {
        String cnm = StringTools.TruncatedClassName(getClass().getName());
        return " /* " + cnm + " " + nodeName + " */ ";
    }

    public String DumpAdap() throws AssertionError {
        String cnm = StringTools.TruncatedClassName(getClass().getName());
        return " ( " + cnm + " " + nodeName + " ) ";
    }

    public void Dump(PrintStream fout) throws IOException, AssertionError {
        fout.print(DumpAdap());
    }

    public void GenCode(PrintStream fout) throws IOException, AssertionError {
        fout.print(DumpC());
    }

    /**
     * Public method for copying AST subtrees.
     * I have to catch AssertionError to fit in the signature of clone().
     */
    public Object clone() throws CloneNotSupportedException {
        //AstNode nt = (AstNode) super.clone();
        AstNode nt = (AstNode) super.freshclone();
        nt.parentAst = null;
        nt.children = new Vector();
        for (int i = 0; i < children.size(); i++) {
            try {
                nt.AddChild((AstNode) ((AstNode) children.elementAt(i)).clone());
            } catch (AssertionError e) {
                /* stop execution, through whatever means */
                throw new CloneNotSupportedException();
            }
        }
        return nt;
    }

    /**
     * Find the index number of myself in my parent's children list.
     */
    int GetMyChildNum() throws AssertionError {
        assert parentAst.children.contains(this);
        int i = parentAst.children.indexOf(this);
        return i;
    }

    /**
     * Insert myself before 'n' in its parent's children list.
     * 'n' and later children are shifted down by 1.
     */
    public void InsertMeBefore(AstNode n) throws AssertionError {
        this.parentAst = n.GetParentAst();
        n.GetParentAst().AddChild(this, n.GetMyChildNum());
    }

    /**
     * Insert myself after 'n' in its parent's children list.
     * Children behind 'n' are shifted down by 1.
     */
    public void InsertMeAfter(AstNode n) throws AssertionError {
        this.parentAst = n.GetParentAst();
        n.GetParentAst().AddChild(this, n.GetMyChildNum() + 1);
    }

    /**
     * Insert me as par's i's child
     */
    public void InsertMeAsChildOf(AstNode par, int i) throws AssertionError {
        this.parentAst = par;
        GetParentAst().AddChild(this, i);
    }

    /**
     * Insert myself as n's last child.
     */
    public void InsertMeAsChildOf(AstNode n) throws AssertionError {
        InsertMeAsChildOf(n, n.GetNumChildren());
    }

    /**
     * Detach myself from my parent's children list.
     */
    public void DetachMe() throws AssertionError {
        GetParentAst().DetachChild(GetMyChildNum());
        parentAst = null;
        UnwireSubtree();
    }

    void UnwireMe() throws AssertionError {
    }

    void UnwireSubtree() throws AssertionError {
        UnwireMe();
        Enumeration chds = GetChildren();
        while (chds.hasMoreElements())
            ((AstNode) chds.nextElement()).UnwireSubtree();
    }

    /**
     * Detach myself and move before 'n' in its parent's children list.
     * 'n' and later children are shifted down by 1.
     */
    public void MoveMeBefore(AstNode n) throws AssertionError {
        DetachMe();
        InsertMeBefore(n);
    }

    /**
     * Detach myself and move after 'n' in its parent's children list.
     * Children behind 'n' are shifted down by 1.
     */
    public void MoveMeAfter(AstNode n) throws AssertionError {
        DetachMe();
        InsertMeAfter(n);
    }

    /**
     * Detach myself and become n's i's child.
     *
     * @param n
     * @throws AssertionError
     */
    public void MoveMeAsChildOf(AstNode n, int i) throws AssertionError {
        DetachMe();
        InsertMeAsChildOf(n, i);
    }

    public void MoveMeAsChildOf(AstNode n) throws AssertionError {
        MoveMeAsChildOf(n, n.GetNumChildren());
    }

    public void CheckConsistency() throws AssertionError, IOException {
        Enumeration chds = GetChildren();
        while (chds.hasMoreElements())
            ((AstNode) chds.nextElement()).CheckConsistency();
    }

    abstract void ReadProgram(OldScanner scanner)
            throws AssertionError, IOException;

    public SegAst GetInnermostScope() throws AssertionError {
        AstNode tblNode = this;
        while (!(tblNode instanceof SegAst) && tblNode != null)
            tblNode = tblNode.GetParentAst();
        return ((SegAst) tblNode);
    }

    public VarAst SearchSymbolTable(String varNm)
            throws AssertionError {
        AstNode tblNode = this;
        while (!(tblNode instanceof SegAst))
            tblNode = tblNode.GetParentAst();
        Hashtable ttbl = ((SegAst) tblNode).GetSymbolTable();
        if (ttbl.containsKey(varNm))
            return (VarAst) ttbl.get(varNm);
        if (tblNode instanceof ProgAst || tblNode.GetParentAst() == null)
            return null;
        return tblNode.GetParentAst().SearchSymbolTable(varNm);
    }

    public boolean IsGlobalVar(String varNm) throws AssertionError {
        VarAst def = SearchSymbolTable(varNm);
        if (def != null && def.GetParentAst() instanceof FileAst) return true;
        else return false;
    }

    /* should not be called from a parameter list of a function, in
       that case, we need to check for its funcBody field, not its
       parents */
    public boolean IsParameterVar(String varNm) throws AssertionError {
        VarAst def = SearchSymbolTable(varNm);
        if (def == null) {
            System.err.println("Error: variable " + varNm + " not found.");
        }
        if (def.GetParentAst() instanceof FuncType) return true;
        else return false;
    }

    public boolean IsLocalVar(String varNm) throws AssertionError {
        VarAst def = SearchSymbolTable(varNm);
        if (def != null && def.GetParentAst() instanceof CodeBlock) return true;
        else return false;
    }

    public BaseType SearchTypeTable(String varNm)
            throws AssertionError {
        AstNode tblNode = this;
        while (!(tblNode instanceof SegAst) && tblNode != null)
            tblNode = tblNode.GetParentAst();
        if (tblNode == null)
            Error("Type name '" + varNm + "' not found. ");
        Hashtable ttbl = ((SegAst) tblNode).GetTypeTable();
        if (ttbl.containsKey(varNm))
            return (BaseType) ttbl.get(varNm);
        if (tblNode instanceof ProgAst)
            return null;
        return tblNode.GetParentAst().SearchTypeTable(varNm);
    }

    void Error(String msg) throws AssertionError {
        System.err.print("Adap Error: " + msg);
        Enumeration attrs = GetAttributes();
        while (attrs.hasMoreElements()) {
            Comment a = (Comment) attrs.nextElement();
            System.err.print(a.GetNodeName() + " " + a.GetComment() + " ");
        }
        System.err.println();
        throw new AssertionError();
    }

    void Warning(String msg) {
        System.err.println("Adap Warning: " + msg);
    }
}
