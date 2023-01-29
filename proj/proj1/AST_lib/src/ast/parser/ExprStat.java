/*
 * ExprStat.java
 *
 * Created on October 8, 2001, 11:33 PM
 */

package ast.parser;

/**
 * @author administrator
 */
public class ExprStat extends ExprAst {

    /**
     * Creates new ExprStat
     */
    public ExprStat(String nm) throws AssertionError {
        super(nm);
    }

    public String DumpAdap() throws AssertionError {
        String str = "(" + ExprStatLabel;
        str += OperandsDumpAdap();
        str += ")\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        String str = super.DumpC();
        return str + ";\n";
    }
}