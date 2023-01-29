package ast.parser;

// BaseType represents any data storage that is not a pointer 
//  (though its fields may contain a pointer)
// Function is a base type

import java.io.IOException;
import java.io.PrintStream;

abstract public class BaseType extends AstNode implements Declarative {
    BaseType(String nm) {
        super(nm);
    }

    /**
     * fnm is the name of the variable/field of this type.  indir is the
     * level of indirection to this function.
     **/
    String GetDataDecl(String varNm, int indir) throws AssertionError {
        String str = "";
        if (this instanceof StructType) str += "struct ";
        str += nodeName;
        for (byte i = 0; i < indir; i++)
            str += "*";
        str += " " + varNm;
        return str;
    }

    void GenDataDecl(PrintStream fout, String varNm, int indir)
            throws AssertionError, IOException {
        fout.print(GetDataDecl(varNm, indir));
    }

}