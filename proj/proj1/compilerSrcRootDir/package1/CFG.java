package package1;

import java.util.ArrayList;
import java.util.List;

public class CFG {

    // the list of basic blocks that make up this CFG
    private final List<BasicBlock> controlFlow;
    // indicates which function this is a CFG for
    private String functionName;

    // constructor
    public CFG(String name) {
        functionName = name;
        controlFlow = new ArrayList<BasicBlock>();
    }


    // get methods
    public String getFunctionName() {
        return functionName;
    }

    // set methods
    public void setFunctionName(String fName) {
        functionName = fName;
    }

    public List<BasicBlock> getFunctionCFG() {
        return controlFlow;
    }

    public void addBasicBlock(BasicBlock b) {
        this.controlFlow.add(b);
    }


    public String toString() {
        return "*** CFG for function: " + functionName + "***\n";
    }

}
