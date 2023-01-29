package ast.parser;

import java.util.Enumeration;

abstract public class OperatorAst extends AstNode implements Calculable {
    // children is a vector of Calculable
    Operator rator;

    OperatorAst(String nm) {
        super(nm);
        rator = new Operator(nm);
    }

    public Operator GetOperator() throws AssertionError {
        assert (rator != null);
        return rator;
    }

    public Enumeration GetOperands() {
        return GetChildren();
    }

    public int GetNumOperands() {
        return GetNumChildren();
    }

    public Calculable GetOperand(int i) {
        return (Calculable) GetChild(i);
    }

    /**
     * Add n as the last operand
     */
    public void AddOperand(Calculable n) throws AssertionError {
        ((AstNode) n).InsertMeAsChildOf(this);
    }

    public Object clone() throws CloneNotSupportedException {
        OperatorAst nt = (OperatorAst) super.clone();
        nt.rator = (Operator) rator.clone();
        return nt;
    }

    public String OperandsDumpAdap() throws AssertionError {
        String str;
        str = rator.DumpC();
        Enumeration opds = GetOperands();
        while (opds.hasMoreElements()) {
            AstNode n = (AstNode) opds.nextElement();
            str += n.DumpAdap();
            str += " ";
        }
        return str;
    }
}