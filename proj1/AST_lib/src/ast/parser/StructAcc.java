package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/** Field access: (StructAcc "." DataAccAst struct_name field_name), where DataAccAst is 
        the access to the base.

    If an access sequence has indirection, e.g. a.b->c, then the indirection
        will be a separate operator, e.g. (Bop (Uop * (BopAcc . a b)) c).
**/

public class StructAcc extends DataAccAst {
    FieldType field;
    private String structNm, fieldNm;

    StructAcc(String nm) throws AssertionError {
        super(nm); assert (nm.compareTo(".")==0);}
    public StructAcc(Addressable base, FieldType fld) 
            throws AssertionError {
        super(".");
        AddBase(base); field = fld;
    }
    
    /** Use GetField() instead 
     */
    public Calculable GetOffset(int i) throws AssertionError {
        assert (false);
        return null;
    }
    
    public FieldType GetField() throws AssertionError {
        if (field==null) {
           StructType st= (StructType) SearchTypeTable(structNm);
           if (st==null)
                Error("Structure type '" + structNm + "' not found");
           field = st.SearchField(fieldNm);
           if (field == null)
                Error("Field "+fieldNm+" not found in "+structNm);
        }
        return field;
    }
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        AddBase((Addressable) ReadNextAstNode(scanner));
        ((AstNode)GetBase()).ReadProgram(scanner);
        IDToken nt = (IDToken) scanner.GetNextToken();
        structNm = nt.GetString();
        nt = (IDToken) scanner.GetNextToken();
        fieldNm = nt.GetString();
        assert (scanner.MatchSym(")"));
    }
    
    public String DumpAdap() throws AssertionError  {
        String str = " ( " + StructAccLabel + " " + nodeName + " ";
        str += GetBase().DumpAdap(); 
        str += " " + GetField().GetStruct().GetNodeName() + 
                    " " + GetField().GetNodeName();
        str += " )\n";
        return str;
    }
    
    public String DumpC() throws AssertionError {
        String str = "";
        if (IsIndirBaseAcc()) {
            str += ((UopAcc)GetBase()).GetBase().DumpC();
            str += "->";
        }
        else {
            str += GetBase().DumpC();
            str += ".";
        }
        str += GetField().GetNodeName()+" ";
        return str;
    }               
    
    void UnwireMe() throws AssertionError {
        field = null;
    }
    
    public void CheckConsistency() throws AssertionError, IOException {
        FieldType old = GetField();
        field = null;
        FieldType cur = GetField();
        if (cur==null) 
            Error("Field access to "+fieldNm+" is invalid");
        else if (cur!=old)
            Error("Inconsistent field access to "+fieldNm);
        super.CheckConsistency();
    }
    
    public boolean equals(Addressable a2) throws AssertionError {
        if (!(a2 instanceof StructAcc)) return false;
        StructAcc aa = (StructAcc) a2;
        if (!GetBase().equals(aa.GetBase())) return false;
        if (GetField()!=aa.GetField()) return false;
        return true;
    }
}