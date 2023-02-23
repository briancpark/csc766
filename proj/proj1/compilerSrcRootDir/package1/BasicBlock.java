package package1;

import ast.parser.*;

import java.util.*;

public class BasicBlock {

    // successors and predecessors of the basic block
    private final List<String> succ;
    private final List<String> pred;
    // statements of the basic block
    private final List<AstNode> statements = new ArrayList<AstNode>(10);
    private final HashMap<String, Integer> valueNumberTable;
    private final HashMap<Integer, AstNode> rewrittenTable;
    private final Set<ExprAst> availableSet;
    private final Set<VarAccAst> varKillSet;
    private final Set<ExprAst> DEExprSet;
    private final Set<ExprAst> exprKillSet;

    // name of the block
    private String blockName;
    // label that the block ends with
    private String endLabel;
    // label that the block starts with
    private String startLabel;

    // constructor
    public BasicBlock(String bName) {
        blockName = bName;
        endLabel = null;
        startLabel = null;
        succ = new ArrayList<String>();
        pred = new ArrayList<String>();
        valueNumberTable = new HashMap<String, Integer>();
        rewrittenTable = new HashMap<Integer, AstNode>();
        availableSet = new HashSet<ExprAst>();
        varKillSet = new HashSet<VarAccAst>();
        DEExprSet = new HashSet<ExprAst>();
        exprKillSet = new HashSet<ExprAst>();
    }

    // get methods
    public String getBlockName() {
        return blockName;
    }

    // set methods
    public void setBlockName(String bName) {
        blockName = bName;
    }

    public String getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(String eLabel) {
        endLabel = eLabel;
    }

    public String getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(String sLabel) {
        startLabel = sLabel;
    }

    public void addSuccessor(String s) {
        this.succ.add(s);
    }

    public void addPredecessor(String p) {
        this.pred.add(p);
    }

    public void addStatement(AstNode node) {
        this.statements.add(node);
    }

    public List<String> getSucc() {
        return succ;
    }

    public List<String> getPred() {
        return pred;
    }

    public Set<ExprAst> getAvailableSet() {
        return availableSet;
    }

    public void setAvailableSet(Set<ExprAst> availableSet) {
        this.availableSet.clear();
        this.availableSet.addAll(availableSet);
    }

    public void DEExprInit() {
        List<AstNode> statements = new ArrayList<>(this.statements);
        Collections.reverse(statements);
        for (AstNode statement : statements) {
            if (statement instanceof AssignStat) {
                List<AstNode> statementChildren = Collections.list(statement.GetChildren());
                assert statementChildren.size() == 2;

                AstNode var = statementChildren.get(0);
                AstNode expr = statementChildren.get(1);

                if (var instanceof VarAccAst) {
                    varKillSet.add((VarAccAst) var);
                }

                if (expr instanceof ExprAst) {
                    List<AstNode> exprChildren = Collections.list(expr.GetChildren());

                    AstNode left = exprChildren.get(0);

                    AstNode right = null;
                    if (exprChildren.size() == 2) {
                        right = exprChildren.get(1);


                    }

                    if (left instanceof VarAccAst && !varKillSet.contains(left)) {
                        if (right != null && right instanceof VarAccAst && !varKillSet.contains(right)) {
                            DEExprSet.add((ExprAst) expr);
                        } else if (right == null) {
                            DEExprSet.add((ExprAst) expr);
                        }
                    }
                }
            }
        }
    }

    public void ExprKillInit() {
        for (AstNode statement : this.statements) {
            if (statement instanceof AssignStat) {
                List<AstNode> statementChildren = Collections.list(statement.GetChildren());
                assert statementChildren.size() == 2;

                AstNode var = statementChildren.get(0);
                AstNode expr = statementChildren.get(1);

                if (expr instanceof ExprAst && !(var instanceof ArrayAcc) && !expr.GetNodeName().equals("FuncCall")) {
                    List<AstNode> exprChildren = Collections.list(expr.GetChildren());

                    for (AstNode child : exprChildren) {
                        if (child instanceof VarAccAst && varKillSet.contains(child)) {
                            exprKillSet.add((ExprAst) expr);
                        }
                    }
                }
            }
        }
    }

    public Set<ExprAst> getDEExprSet() {
        return DEExprSet;
    }

    public Set<ExprAst> getExprKillSet() {
        return exprKillSet;
    }

    public void valueNumbering() {
        int valueNumber = 0;
        int renameNumber = 0;
        for (int i = 0; i < statements.size(); i++) {
            AstNode statement = statements.get(i);
            if (statement instanceof AssignStat) {
                List<AstNode> statementChildren = Collections.list(statement.GetChildren());
                assert statementChildren.size() == 2;

                AstNode var = statementChildren.get(0);
                AstNode expr = statementChildren.get(1);

                // Process the expression
                AstNode left = null, right = null;
                int exprValueNumber, leftValueNumber = -1, rightValueNumber = -1;

                if (expr instanceof ExprAst && !(var instanceof ArrayAcc) && !expr.GetNodeName().equals("FuncCall")) {
                    List<AstNode> exprChildren = Collections.list(expr.GetChildren());

                    left = exprChildren.get(0);

                    if (exprChildren.size() == 2) {
                        right = exprChildren.get(1);
                    }

                    if (left instanceof VarAccAst || left instanceof ConstAst) {
                        if (valueNumberTable.containsKey(left.DumpC())) {
                            leftValueNumber = valueNumberTable.get(left.DumpC());
                        } else {
                            leftValueNumber = valueNumber++;
                            valueNumberTable.put(left.DumpC(), leftValueNumber);
                            rewrittenTable.put(leftValueNumber, left);
                        }
                    }

                    if (right != null) {
                        if (right instanceof VarAccAst || right instanceof ConstAst) {
                            if (valueNumberTable.containsKey(right.DumpC())) {
                                rightValueNumber = valueNumberTable.get(right.DumpC());
                            } else {
                                rightValueNumber = valueNumber++;
                                valueNumberTable.put(right.DumpC(), rightValueNumber);
                                rewrittenTable.put(rightValueNumber, right);
                            }
                        }
                    }
                } else if (expr instanceof ArrayAcc) {
                    if (valueNumberTable.containsKey(expr.DumpC())) {
                        leftValueNumber = valueNumberTable.get(expr.DumpC());
                    } else {
                        leftValueNumber = valueNumber++;
                        valueNumberTable.put(expr.DumpC(), leftValueNumber);
                        rewrittenTable.put(leftValueNumber, expr);
                    }
                } else if (expr instanceof VarAccAst || expr instanceof ConstAst) {
                    left = expr;
                    if (valueNumberTable.containsKey(left.DumpC())) {
                        leftValueNumber = valueNumberTable.get(left.DumpC());
                    } else {
                        leftValueNumber = valueNumber++;
                        valueNumberTable.put(left.DumpC(), leftValueNumber);
                        rewrittenTable.put(leftValueNumber, left);
                    }
                }

                String valueNumberID = String.valueOf(leftValueNumber);
                if (rightValueNumber != -1) {
                    valueNumberID += expr.GetNodeName() + rightValueNumber;
                }

                // Process the variable
                if (var instanceof VarAccAst) {
                    // Value renaming
                    if (valueNumberTable.containsKey(var.DumpC())) {
                        // Rename variable by inserting a new value number, to make it unique
                        valueNumberTable.put(var.DumpC(), valueNumber++);
                        rewrittenTable.put(valueNumberTable.get(var.DumpC()), var);
                    } else {
                        if (valueNumberTable.containsKey(valueNumberID)) {
                            valueNumberTable.put(var.DumpC(), valueNumberTable.get(valueNumberID));
                        } else {
                            exprValueNumber = valueNumber++;
                            valueNumberTable.put(valueNumberID, exprValueNumber);
                            valueNumberTable.put(var.DumpC(), exprValueNumber);
                            rewrittenTable.put(exprValueNumber, var);
                        }
                    }
                }
            }
        }
    }

    public void rewrite() {
        // Rewrite the AST using the rewrittenTable and valueNumberTable
        for (AstNode statement : statements) {
            if (statement instanceof AssignStat) {
                assert (statement.GetNumChildren() == 2);

                List<AstNode> statementChildren = Collections.list(statement.GetChildren());

                // This is under the assumption that the variable is the first child
                AstNode var = statementChildren.get(0);
                AstNode expr = statementChildren.get(1);

                assert (var instanceof VarAccAst || var instanceof ArrayAcc);
                assert (expr instanceof VarAccAst || expr instanceof ExprAst || expr instanceof ConstAst || expr instanceof ArrayAcc);

                // If variable can be rewritten, replace it with the rewritten variable using the rewrittenTable
                if (var instanceof VarAccAst && expr instanceof ExprAst) {
                    String varName = var.DumpC();
                    int valueNumber = valueNumberTable.get(varName);
                    AstNode rewrittenNode = rewrittenTable.get(valueNumber);

                    if (varName != rewrittenNode.DumpC()) {
                        // Rewrite statement with ASTNode with rewrittenVarName
                        List<AstNode> assmntChildren = Collections.list(var.GetParentAst().GetChildren());
                        AstNode exprAST = assmntChildren.get(1);
                        assert (exprAST instanceof ExprAst);
                        exprAST.DetachMe();
                        rewrittenNode.InsertMeAsChildOf(statement, 1);
                    }
                }
            }
        }
    }

    public String toString() {

        String blockDescription = "// " + this.blockName + ":\n";

        // determine if the BB has 0, one or more predecessors
        if (pred.isEmpty())
            blockDescription += "//   Predecessors: None";
        else if (pred.size() == 1)
            blockDescription += "//   Predecessor: ";
        else
            blockDescription += "//   Predecessors: ";


        // list the predecessors of the basic block
        for (String predecessor : pred)
            blockDescription += predecessor + " ";

        // determine if the BB has 0, one or more successors
        if (succ.isEmpty())
            blockDescription += "\n//   Successors: None";
        else if (succ.size() == 1)
            blockDescription += "\n//   Successor: ";
        else
            blockDescription += "\n//   Successors: ";


        // list the successors of the basic block
        for (String successors : succ)
            blockDescription += successors + " ";

        blockDescription += "\n";

        // list the statements in the basic block
        for (AstNode s : statements)
            blockDescription += s.DumpC();

        return blockDescription;
    }

}