package package1;

import ast.parser.AstNode;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock {

    // name of the block
    private String blockName;

    // label that the block ends with
    private String endLabel;

    // label that the block starts with
    private String startLabel;

    // successors and predecessors of the basic block
    private final List<String> succ;
    private final List<String> pred;

    // statements of the basic block
    private final List<AstNode> statements = new ArrayList<AstNode>(10);

    // constructor
    public BasicBlock(String bName) {
        blockName = bName;
        endLabel = null;
        startLabel = null;
        succ = new ArrayList<String>();
        pred = new ArrayList<String>();
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
