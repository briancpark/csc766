package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.StringToken;

import java.io.IOException;
import java.util.Enumeration;

/* (ExprAst oprator_string (Calculable)* ), the last part is a list of
    operands 
    operator can be +, -, etc
    It is a function call if the string is FuncCall.  The first
    expression is the function name/access path.  */

public class ExprAst extends OperatorAst {
    public ExprAst(String nm) throws AssertionError {
        super(nm);
        if (!nm.equals("FuncCall") &&
                ast.scanner.Lexi.isIdChar((int) nm.charAt(0))) {
            // Treat it as a function call
            nodeName = "FuncCall";
            AddOperand(new VarAccAst(nm));
        }
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        StringToken nt = (StringToken) scanner.PreviewNextToken();
        while (nt.GetString().compareTo(")") != 0) {
            Calculable nd = (Calculable) ReadNextAstNode(scanner);
            ((AstNode) nd).ReadProgram(scanner);
            AddOperand(nd);
            nt = (StringToken) scanner.PreviewNextToken();
        }
        scanner.MatchSym(")");
    }

    public String DumpAdap() throws AssertionError {
        String str = "(" + ExprAstLabel;
        str += OperandsDumpAdap();
        str += ")\n";
        return str;
    }

    public String DumpC() throws AssertionError {
        String str = "";
        if (IsFuncCall()) {
            Enumeration opds = GetOperands();
            Addressable func = (Addressable) opds.nextElement();
            if (func instanceof VarAccAst)
                // just function name
                str += ((AstNode) func).GetNodeName();
            else
                str += func.DumpC();
            str += "(";
            while (opds.hasMoreElements()) {
                AstNode n = (AstNode) opds.nextElement();
                str += n.DumpC();
                if (opds.hasMoreElements()) str += ", ";
            }
            str += ")";
            return str;
        }
        if (GetNumOperands() == 1) {
            str += "(";
            str += rator.DumpC();
            str += GetOperand(0).DumpC();
            str += ")";
            return str;
        }
        if (GetNumOperands() == 2) {
            str += "(";
            str += GetOperand(0).DumpC();
            str += rator.DumpC();
            str += GetOperand(1).DumpC();
            str += ")";
            return str;
        } else {
            java.lang.System.err.println("Do not how to generate code for ExprAst with node name " + GetNodeName() + ".");
            assert (false);  // do not know how to generate code
        }
        return str;
    }

    /* see if this is a call to a function */
    public boolean IsFuncCall() throws AssertionError {
        return nodeName.compareTo("FuncCall") == 0;
    }

    public boolean IsFuncCall(String funcName) throws AssertionError {
        if (nodeName.compareTo("FuncCall") != 0) return false;
        if (GetNumOperands() < 1) return false;
        AstNode nt = (AstNode) GetOperand(0);
        if (!(nt instanceof VarAccAst)) return false;
        if (((VarAccAst) nt).GetNodeName().compareTo(funcName) != 0)
            return false;
        return true;
    }

}