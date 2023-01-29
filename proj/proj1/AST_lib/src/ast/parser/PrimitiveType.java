package ast.parser;

import ast.scanner.OldScanner;

import java.io.IOException;

public class PrimitiveType extends BaseType {
    public PrimitiveType(String nm) {
        super(nm);
    }

    void ReadProgram(OldScanner scanner) throws AssertionError, IOException {
        throw new AssertionError();
    }

    public String DumpAdap() {
        String str = " ( " + PrimitiveTypeLabel + " " + nodeName + " ";
        str += " )";
        return str;
    }

    public String DumpC() {
        return nodeName;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}        
