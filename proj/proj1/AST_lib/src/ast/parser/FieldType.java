package ast.parser;

import ast.scanner.OldScanner;

import java.io.IOException;

/**
 * (FieldType field_name DataType) or (FieldType field_name ArrayType), the
 * first is the type of the field. Its parent is the structure in which the
 * field is defined.
 **/

// Representing variable definition, not its reference
// Representing a function variable when var_type is FuncType
// If it's an array with know dimensions, then use ArrayVar node

public class FieldType extends BaseType {
    FieldType(String vnm) throws AssertionError {
        super(vnm);
    }

    public FieldType(String vnm, DataType dt) throws AssertionError {
        super(vnm);
        AddChild(dt);
    }

    public DataType GetFieldType() throws AssertionError {
        return (DataType) GetChild(0);
    }

    public StructType GetStruct() {
        return (StructType) GetParentAst();
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        DataType fieldType = (DataType) ReadNextAstNode(scanner);
        //assert (fieldType instanceof DataType || 
        //                 fieldType instanceof ArrayType);
        fieldType.ReadProgram(scanner);
        AddChild(fieldType);
        assert (scanner.MatchSym(")"));
    }

    public String DumpAdap() throws AssertionError {
        String str = " ( " + FieldTypeLabel + " " + nodeName + " ";
        str += GetFieldType().DumpAdap();
        str += " )\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        return GetFieldType().GetDataDecl(nodeName);
    }

}