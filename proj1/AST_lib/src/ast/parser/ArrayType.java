package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;

/** (ArrayType "array" DataType [int]*), DataType is the type of array elements.
    A vector of integers represent the dimensions of the array.
    The field 'recordedNames' is a set of array types that have been declared in
    this file.  The field is reset at FileAst.DumpC().  The reason for this is
    that C cannot directly declare a function returning a pointer to an array,
    we have to do: typedef int A[5]; A *(foo)(int i);
**/
public class ArrayType extends BaseType {
    Vector dimensions;
    public DataType GetElemType() throws AssertionError {
        return (DataType) GetChild(0);
    }
    public Vector GetDimensions() {return dimensions;}
    public int GetNumElements() {
        int num = 1;
        for (int i=0; i<dimensions.size(); i++) 
            num *= ((Integer) dimensions.elementAt(i)).intValue();
        return num;
    }
    
    ArrayType(String vnm) {
        super(vnm); dimensions = new Vector();
    }
    
    public ArrayType(String vnm, DataType dt, Vector dim) throws AssertionError {
        super(vnm); AddChild(dt); dimensions = dim;
    }
    
    // Handle e.g. a[][5], use -1 to indicate a star at the first dimension
    void AddFirstDimension() {
        dimensions.insertElementAt(new Integer(-1), 0);
    }
        
    void ReadProgram(OldScanner scanner) 
                    throws IOException, AssertionError {
        // Read data type
        DataType elemType = (DataType) ReadNextAstNode(scanner);
        elemType.ReadProgram(scanner);
        AddChild(elemType);
        
        // Read dimensions
        StringToken indt = (StringToken) scanner.GetNextToken();
        if (indt.GetString().compareTo("*")==0) {
            dimensions.add(new Integer(-1));
            indt = (StringToken) scanner.GetNextToken();
        }
        while (indt instanceof NumToken) {
            dimensions.add(new Integer(indt.GetString()));
            indt = (StringToken) scanner.GetNextToken();
        }
        assert ((StringToken)indt).GetString().compareTo(")")==0;
    }
    
    public String DumpAdap() throws AssertionError {
        String str = " ( " + ArrayTypeLabel + " " + "array" + " ";
        str += GetElemType().DumpAdap(); 
        for (int i=0; i<dimensions.size(); i++) {
            if (((Integer) dimensions.elementAt(i)).intValue()==-1)
                str += " *";
            else str += " " + (Integer) dimensions.elementAt(i);
        }
        str += " ) ";
        return str;
    }
    
    public void GenCode(PrintStream fout) throws IOException, AssertionError {
        assert false;  // Need up-level info on variable/field name
    }        
    
    /** fnm is the name of the variable/field of this type.  indir is the
        level of indirection to this function.
    **/
    public String GetDataDecl(String fnm, int indir) throws AssertionError {
            String decl;
            // We cannot declare an array of arrays or functions.
            if (indir > 0) {
               decl = GetTypeDecl();
               for (int i=0; i<indir; i++) decl += "*";
            }
            else decl = GetElemType().GetTypeDecl();
            decl += " " + fnm;
            if (indir > 0 ) return decl;
            for (int d=0; d<dimensions.size(); d++) {
                if (((Integer)dimensions.elementAt(d)).intValue()==-1)
                    decl += "[]";
                else decl += "["+(Integer) dimensions.elementAt(d)+"]";
            }
            return decl;
        }
    
    /** Array type cloning is allowed because it has no representation
     *    in the type table.
     */
    public Object clone() throws CloneNotSupportedException {
        ArrayType nt = (ArrayType) super.clone();
        nt.dimensions = (Vector) dimensions.clone();
        return nt;
    } 

        /* Index expression for the first element of the array.  A new variable
            is created and then deleted.  The name postfix "_sample" is 
            defined in VarTypeRecorder class in Reda.  Here we hardware the name
            so compilation does not depend on other packages.  */
    public Addressable GetFirstElemAst(Addressable refBase) throws AssertionError {
            ArrayAcc acc = new ArrayAcc(refBase);
            for (int i=1; i<GetDimensions().size(); i++) 
                acc.AddOffset(new ConstAst(String.valueOf(0)));
            acc.AddOffset(new ConstAst(String.valueOf(1)));
            return acc;
    }

    String GetTypeDecl() throws AssertionError {
        String anm = tools.StringTools.ConvertToIdentifier(GetDataDecl("A", 0));
        if (declaredNames.containsKey(anm))
            return anm;
        else {
            String dcl = "typedef "+GetDataDecl(anm,0)+";\n";
            declaredNames.put(anm, anm);
            return dcl+anm;
        }
    }
       
    /* See explanation at the beginning. */
    static Hashtable declaredNames;

}