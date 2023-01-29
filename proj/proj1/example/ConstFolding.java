import ast.parser.AstNode;
import ast.parser.ConstAst;
import ast.parser.ExprAst;
import ast.parser.ProgAst;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Find operations between constants, e.g. 1+2, calculate the result,
 * and fold it into the program.  Check for addition only in the current
 * version.
 */
class ConstFolding {
    public static void main(String[] args) throws AssertionError {
        // check the argument
        if (args.length != 1) {
            System.err.println("constFold prog_name.adap");
            throw new AssertionError();
        }

        // convert the input into AST rooted at prog
        ProgAst prog = new ProgAst(args[0]);

        // constant folding
        FoldConst(prog);

        // code generation
        try {
            prog.GenCode();
        } catch (IOException e) {
            System.err.println("Fail to generate the output file.");
            throw new AssertionError();
        }
    }

    /**
     * Recursively check and replace constant addition operation
     */
    static void FoldConst(AstNode node) throws AssertionError {

        /** Fold constants in children */
        Enumeration myChildren = node.GetChildren();
        while (myChildren.hasMoreElements())
            FoldConst((AstNode) myChildren.nextElement());

        /** Check myself **/
        /* Step 1: test if this is a two-operand addition */
        if (!(node instanceof ExprAst)) return;
        ExprAst expr = (ExprAst) node;
        String operator = expr.GetOperator().GetOpName();
        if (operator.compareTo("+") != 0) return;
        if (expr.GetNumOperands() != 2) return;

        /* Step 2: test if operands are integer constants */
        Enumeration operands = expr.GetOperands();
        Object op1 = operands.nextElement();
        Object op2 = operands.nextElement();
        if (!(op1 instanceof ConstAst && op2 instanceof ConstAst))
            return;
        ConstAst cst1 = (ConstAst) op1;
        ConstAst cst2 = (ConstAst) op2;
        if (!(cst1.IsIntConst() && cst2.IsIntConst())) return;

        /* Step 3: calculate */
        int result = cst1.GetIntConst() + cst2.GetIntConst();

        /* Step 4: change Ast */
        ConstAst newNode = new ConstAst(String.valueOf(result));
        newNode.InsertMeBefore(node);
        node.DetachMe();

    }

}
