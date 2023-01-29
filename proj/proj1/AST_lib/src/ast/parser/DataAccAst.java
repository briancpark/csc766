package ast.parser;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Root class for aggregated data access,
 * i.e. take pointer or dereference, array or structure access.
 * The operators are "&", "*", and ".".
 * It has a base (operand(0)) and zero or more offsets.
 * If an access sequence has indirection, e.g. a.b->c, then the indirection
 * will be a separate operator, e.g. (StructAcc (Uop * (StructAcc . a b)) c).
 */
abstract public class DataAccAst extends OperatorAst implements Addressable {
    DataAccAst(String nm) {
        super(nm);
    }

    /**
     * Whether the array or structure access needs to dereference the base.
     */
    public boolean IsIndirBaseAcc() throws AssertionError {
        if (GetNumOffsets() == 0) return false;
        if (!(GetBase() instanceof UopAcc)) return false;
        if (((UopAcc) GetBase()).IsDereferencing()) return true;
        else return false;
    }


    public Addressable GetBase() throws AssertionError {
        if (GetNumChildren() == 0) return null;
        return (Addressable) GetOperand(0);
    }

    public int GetNumOffsets() throws AssertionError {
        int n = GetNumOperands() - 1;
        assert (n >= 0);
        return n;
    }

    public Calculable GetOffset(int i) throws AssertionError {
        return (Calculable) GetOperand(i + 1);
    }

    public Enumeration GetOffsets() throws AssertionError {
        Vector ofts = new Vector();
        for (int i = 0; i < GetNumOffsets(); i++)
            ofts.addElement(GetOffset(i));
        return ofts.elements();
    }

    /**
     * Base can only be added once before offsets.
     */
    void AddBase(Addressable opd) throws AssertionError {
        assert (GetBase() == null);
        AddOperand(opd);
    }

    /**
     * Each offset can be added once after its precursors.
     */
    void AddOffset(Calculable oft) throws AssertionError {
        assert (GetBase() != null);
        AddOperand(oft);
    }

    public boolean equals(Addressable a2) throws AssertionError {
        throw new AssertionError();
    }
}