package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/** Array access: (ArrayAcc "." Addressable AddressOffset), the second
 * is array offset.  For int A[M][N], the access of A[2][2] will be
 * (ArrayAcc "." (ArrayAcc "." (Const 2)) (Const 2)).  We will change
 * it to (ArrayAcc "." (Const 2) (Const 2)).  Our version of lcc will
 * not allow A[0][0] to be referenced as **A.  So high-dimensional
 * array accesses are represented only in this way.
 *
 * Collapsing consecutive ArrayAcc's is appropriate because they must
 * be accessing a single multi-dimensional array, even in the following
 * example:
 *  typedef int a[10];  a b[10]; b[0][1]=3;  in which b is int[10][10].
 *
 */

public class ArrayAcc extends DataAccAst {
    // IndexVector offsets;  // vector of Calculable, which is the offset of BopAcc
    ArrayAcc(String nm) {super(nm);}
        
    public ArrayAcc(Addressable base) 
            throws AssertionError {
       super(".");
       AddBase(base);
    }
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        Addressable base = (Addressable) ReadNextAstNode(scanner);
        ((AstNode)base).ReadProgram(scanner);
        if (base instanceof ArrayAcc) {
            //  We merge consecutive ArrayAccs here.  If we merge too
            //   much, CheckConsistency will resplit them appropriately.
            AstNode newbase = (AstNode) ((ArrayAcc)base).GetBase();
            Enumeration ofts = ((ArrayAcc)base).GetOffsets();
            newbase.MoveMeAsChildOf(this);
            while (ofts.hasMoreElements()) {
                 AstNode oft = (AstNode) ofts.nextElement();
                 oft.MoveMeAsChildOf(this);
            }
        }
        else AddBase(base);
        Calculable offset = (Calculable) ReadNextAstNode(scanner);
        ((AstNode)offset).ReadProgram(scanner);
        AddOffset(offset);
        StringToken nn = (StringToken) scanner.PreviewNextToken();
        while (nn.GetString().compareTo(")")!=0) {
            offset = (Calculable) ReadNextAstNode(scanner);
            ((AstNode)offset).ReadProgram(scanner);
            AddOffset(offset);
            nn = (StringToken) scanner.PreviewNextToken();
        }
        assert scanner.MatchSym(")");
    }
    
    public String DumpAdap() throws AssertionError {
        String str = " ( " + ArrayAccLabel + " " + nodeName + " ";
        str += GetBase().DumpAdap(); 
        for (int i=0; i<GetNumOffsets(); i++) 
            str += GetOffset(i).DumpAdap();
        str +=" )";
        return str;
    }
    
    public String DumpC() throws AssertionError {
        String str="";
        AstNode bas = (AstNode) GetBase(); 
        if (bas instanceof UopAcc && ((UopAcc)bas).IsDereferencing() 
                && ((UopAcc)bas).GetBase() instanceof VarAccAst) {
            // Array base: remove extra * if it is parameter
            VarAccAst bas2 = (VarAccAst) ((UopAcc)bas).GetBase();
            if (bas2.IsParameterVar(bas2.GetNodeName())) bas = bas2;
        }
        
        // Generate extra () if it needs dereference
        if (bas instanceof UopAcc && ((UopAcc)bas).IsDereferencing()) {
            str += "(";
            str += bas.DumpC();
            str += ")";
        }
        else str += bas.DumpC();
        
        for (int i=0; i<GetNumOffsets(); i++) {
             str += "[";
             str += GetOffset(i).DumpC();
             str += "]";
        }
        str += " ";
        
        return str;
    }  
    
    public boolean equals(Addressable a2) throws AssertionError {
        if (!(a2 instanceof ArrayAcc)) return false;
        ArrayAcc aa = (ArrayAcc) a2;
        if (!GetBase().equals(aa.GetBase())) return false;
        for (int i=0; i<GetNumOffsets(); i++) {
            if (!(GetOffset(i) instanceof Addressable)) return false;
            if (! ((Addressable)GetOffset(i)).equals(aa.GetOffset(i))) 
                return false;
        }
        return true;
    }
    
    public boolean IsDereferencing() throws AssertionError {
        boolean isderef = GetBaseRemainingDim()==0;
        if (!isderef) return false;
        assert GetNumOffsets()==1;
        return true;
    }
    
    int GetBaseRemainingDim() throws AssertionError {
        AstNode base = (AstNode) GetBase();
        if (base instanceof ArrayAcc) {
            return ((ArrayAcc) base).GetRemainingArrayDim();
        }
        if (base instanceof StructAcc || base instanceof VarAccAst) {
            DataType dt;
            if (base instanceof StructAcc) 
                dt = ((StructAcc)base).GetField().GetFieldType();
            else 
                dt = ((VarAccAst)base).GetVar().GetVarType();
            int res = dt.GetNumArrayDim();
            return res;
        }
        if (base instanceof UopAcc) 
            // array base cannot come from UopAcc
            throw new AssertionError();
        else  // no other cases either
            throw new AssertionError();
    }
        
    
    /* The number of array dimensions that have not been indexed so far */
    public int GetRemainingArrayDim() throws AssertionError {
        AstNode base = (AstNode) GetBase();
        int numo = GetNumOffsets();
        int res = GetBaseRemainingDim();
        if (res == 0) return 0;  // dereferencing a pointer
        if (!(res-numo>=0)) 
            throw new AssertionError();
        return res - numo;
    }
    
    public void CheckConsistency() throws AssertionError {
        int numo = GetNumOffsets();
        int res = GetBaseRemainingDim();
        if (res-numo>=0) return;  // Partial array access, fine.
        
        // Otherwise, the ArrayAcc needs to be split
        //  E.g. a[i][j], but a is of char** type.
        do {
            AstNode newbase = new ArrayAcc(".");
            AstNode base = (AstNode) GetBase();
            newbase.InsertMeBefore(base);
            base.MoveMeAsChildOf(newbase);
            // move res offsets over
            Enumeration ofts = GetOffsets();
            for (int d=0; d<res; d++) {
                AstNode oft = (AstNode) ofts.nextElement();
                oft.MoveMeAsChildOf(newbase);
            }
            numo = GetNumOffsets();
            res = GetBaseRemainingDim();            
        } while (res!=0 && numo-res>0);
    }
}
