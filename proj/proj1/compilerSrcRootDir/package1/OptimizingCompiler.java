package package1;

//import java.lang.*;

import ast.parser.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
//import tools.*; 

// if you import with keyword static do not have to prefix the class name to static methods of class TrivialSetup
//import static package1.TrivialSetup.*; 

public class OptimizingCompiler {

    // contains the CFG for each function in the program
    public static List<CFG> programFlow = new ArrayList<CFG>();
    private static int bbCounter;

    public static void main(String[] args) throws AssertionError {
        // check the arguments
        if (args.length != 3) {
            System.err.println("Please put the adap filename (WITHOUT file extension) as argument to the program");
            return;
        }

        File inputFile = new File(args[0] + ".adap");
        if (!inputFile.isFile()) {
            System.err.println("File does not exists. Please put the adap filename (WITHOUT file extension) as argument to the program");
            return;
        }

        ProgAst prog = new ProgAst(args[0] + ".adap");

        try {
            File outputFile = new File(args[1]);
            PrintStream out = new PrintStream(new FileOutputStream(outputFile));

            // Prints the Abstract Syntax Tree
            // TrivialSetup.printTree( prog );
            // TrivialSetup.recursivePrintTree(prog);

            // Phase 2
            // checks for specific nodes and handle their transformations into assembly like code
            convertToAssembly(prog);

            // Phase 3
            // construct a CFG for each function in the program
            constructCFG(prog);

            // Phase 3
            // Perform redundancy elimination via value numbering on the CFG
//            valueNumbering(prog);

            // Phase 1
            // insert calls to recordInst() method into the Abstract Syntax Tree
            TrivialSetup.insertRecordInstCall(prog);

            // Phase 1
            // declare and define init(), recordInst(), report() and
            // the global counter variable
            TrivialSetup.defineDeclareTrivialMethods(prog);

            // Phase 1
            // insert a call to the init() method
            TrivialSetup.insertInitCall(prog);


            // Phase 3
            // print the CFG into files with extension .cfg
            File outputCfgFile = new File(args[2]);
            PrintStream fout = new PrintStream(new FileOutputStream(outputCfgFile));

            for (CFG c : programFlow) {
                fout.println(c);

                Iterator basicBlocks = c.getFunctionCFG().iterator();

                while (basicBlocks.hasNext())
                    fout.println(basicBlocks.next().toString());
            }


            // an output C file will be dumped with extension .new.c
            prog.GenCode(out);
            out.close();
        } catch (IOException e) {
            System.err.println("Failed to generate output file.");
        }
    }

    public static void valueNumbering(AstNode node) {
        // Store values into a value number table
        for (CFG c : programFlow) {
            for (BasicBlock b : c.getFunctionCFG()) {
                b.valueNumbering();
            }
        }

        // Rewrite the AST with the value number table
        for (CFG c : programFlow) {
            for (BasicBlock b : c.getFunctionCFG()) {
                b.rewrite();
            }
        }
    }


    /*
     * Locate a function definition in the AST, pass the node for the root of
     * the function to the createFunctionCFG() method
     */


    public static void constructCFG(AstNode node) {

        Stack<AstNode> treeStack = new Stack<AstNode>();
        Stack<AstNode> treeRestack = new Stack<AstNode>();

        treeStack.push(node);

        while (!treeStack.empty()) {

            AstNode nodeToCheck = treeStack.pop();

            // find functions in the AST and create the CFG for each function
            if (nodeToCheck instanceof FuncAst && nodeToCheck.GetNumChildren() != 0) {
                // create the CFG for the found function
                constructFunctionCFG(nodeToCheck);
            }

            // get the children so that it traverses the entire tree
            Enumeration children = nodeToCheck.GetChildren();
            int numChildren = nodeToCheck.GetNumChildren();

            for (int i = 1; i <= numChildren; i++) {
                AstNode tempChild = (AstNode) children.nextElement();
                treeRestack.push(tempChild);
            }

            while (!treeRestack.empty())
                treeStack.push(treeRestack.pop());
        }
    }

    /*
     *  Receives a node that is the root of a function definition in the AST
     *  creates a CFG for the function
     *
     */

    public static void constructFunctionCFG(AstNode node) {

        bbCounter = 0;

        CFG funcCFG = new CFG(node.GetNodeName());

        programFlow.add(funcCFG);

        BasicBlock aBlock = null;

        Stack<AstNode> treeStack = new Stack<AstNode>();
        Stack<AstNode> treeRestack = new Stack<AstNode>();

        treeStack.push(node);

        boolean inBlock = false;

        // did a BB end in a conditional jump
        boolean wasConditionalJump = false;

        // did a BB end in a function call
        boolean wasFunctionCall = false;

        while (!treeStack.empty()) {

            AstNode nodeToCheck = treeStack.pop();

            if (!inBlock) // not within a Basic Block
            {
                // a BB is started
                aBlock = new BasicBlock("Block " + bbCounter);

                if (nodeToCheck instanceof FuncAst) // not in BB and beginning of a function declaration
                {
                    // the entry BB of the CFG
                    aBlock.addPredecessor("Entry");
                    inBlock = true;

                } else if (nodeToCheck instanceof LabelStat) // not in BB and a label is found
                {
                    // if the previous block ended in a conditional branch or a function call
                    // then it is this new block's predecessor
                    if (wasConditionalJump || wasFunctionCall)
                        aBlock.addPredecessor("Block " + (bbCounter - 1));

                    // add the label to the statement list of the BB
                    aBlock.addStatement(nodeToCheck);

                    // indicate the name of the label the BB starts with
                    aBlock.setStartLabel(nodeToCheck.GetNodeName());

                    inBlock = true;

                } else if (nodeToCheck instanceof GotoStat && nodeToCheck.GetNumChildren() == 0) //not in BB & a jump is found
                {
                    // when you find a jump outside of a BB then you create a new BB that immediately ends

                    // add the jump instruction into the statement list of the BB
                    aBlock.addStatement(nodeToCheck);

                    // if the previous block ended in a conditional jump or a function call then this
                    // block's predecessor is that previous block
                    if (wasConditionalJump || wasFunctionCall)
                        aBlock.addPredecessor("Block " + (bbCounter - 1));

                    // mark the jump label that ends this BB
                    aBlock.setEndLabel(nodeToCheck.GetNodeName());

                    // add the BB into the funcCFG that holds the CFG list for this function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;

                    // inBlock remains false
                    inBlock = false;
                    wasConditionalJump = false;
                    wasFunctionCall = false;

                } else if (nodeToCheck instanceof GotoStat)   // not in BB and a conditional branch encountered
                {
                    // a new BB is created and immediately ends because this is a branch statement

                    // add the if statement into the BB
                    aBlock.addStatement(nodeToCheck);

                    // if the previous block ended in a conditional branch or a function call then
                    // it is the predecessor of this block
                    if (wasConditionalJump || wasFunctionCall)
                        aBlock.addPredecessor("Block " + (bbCounter - 1));

                    // since this is a conditional branch one of its successors is the next block
                    aBlock.addSuccessor("Block " + (bbCounter + 1));

                    // mark the label name that the branch ends with
                    aBlock.setEndLabel(nodeToCheck.GetNodeName());

                    // add this BB into the control flow for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    // inBlock remains false
                    inBlock = false;

                    wasConditionalJump = true;
                    wasFunctionCall = false;

                } else if (nodeToCheck instanceof ExprStat && nodeToCheck.GetNodeName().equals("FuncCall")) // not in BB & func call outside
                { // an assignment statement

                    // the BB will start with this function call and end with it

                    // add the statement into the BB
                    aBlock.addStatement(nodeToCheck);

                    // if the previous block ended in a conditional branch or a function call then it is this block's predecessor
                    if (wasConditionalJump || wasFunctionCall)
                        aBlock.addPredecessor("Block " + (bbCounter - 1));

                    // its successor should be the next block
                    aBlock.addSuccessor("Block " + (bbCounter + 1));

                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    inBlock = false;
                    wasFunctionCall = true;
                    wasConditionalJump = false;

                } else if (nodeToCheck instanceof AssignStat) // not in BB and an assignment statement is found
                {
                    // add the statement into the block
                    aBlock.addStatement(nodeToCheck);

                    // if the previous block ended in a conditional branch or a function call then it is this block's predecessor
                    if (wasConditionalJump || wasFunctionCall)
                        aBlock.addPredecessor("Block " + (bbCounter - 1));

                    inBlock = true;

                } else if (nodeToCheck instanceof ReturnStat) // not in BB and detect a return statement
                {
                    // add the return statement into the BB
                    aBlock.addStatement(nodeToCheck);

                    // mark its successor as EXIT
                    aBlock.addSuccessor("Exit");

                    // if the previous block was a conditional jump then it is this block's predecessor
                    if (wasConditionalJump || wasFunctionCall)
                        aBlock.addPredecessor("Block " + (bbCounter - 1));

                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    // still not in BB inBlock remains false
                    wasConditionalJump = false;
                    wasFunctionCall = false;

                }
            } else { /* we are in a BB and looking at statements */

                if (nodeToCheck instanceof FuncAst && nodeToCheck.GetNumChildren() == 0) // in BB and end of a function definition
                {
                    // 1 successor the EXIT node
                    aBlock.addSuccessor("Exit");

                    // add the block to the CFG for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    // BB ends
                    inBlock = false;
                    wasConditionalJump = false;
                    wasFunctionCall = false;

                } else if (nodeToCheck instanceof LabelStat) // in a BB and we see a label
                {
                    // current BB ends because we encounter a new label
                    // we know the current blocks's one and only successor is the next upcoming labeled block

                    aBlock.addSuccessor("Block " + (bbCounter + 1));

                    // add this ending BB into the CFG for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;

                    // start a new BB, it begins with the label we have detected
                    aBlock = new BasicBlock("Block " + bbCounter);

                    // mark the label name for the start of this BB, it could be the target of a jump
                    aBlock.setStartLabel(nodeToCheck.GetNodeName());

                    // know that this guys predecessor is the previous block that just ended because of this new label
                    aBlock.addPredecessor("Block " + (bbCounter - 1));

                    // insert the label in to the BB
                    aBlock.addStatement(nodeToCheck);


                    // inBlock never changed

                } else if (nodeToCheck instanceof GotoStat && nodeToCheck.GetNumChildren() == 0) // in a BB and we see a jump
                {

                    //add the jump statement into the block
                    aBlock.addStatement(nodeToCheck);

                    // mark the end jump label of the BB
                    aBlock.setEndLabel(nodeToCheck.GetNodeName());

                    // end the BB and add it to the CFG for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    // BB ends
                    inBlock = false;
                    // How did it end
                    wasConditionalJump = false;
                    wasFunctionCall = false;

                } else if (nodeToCheck instanceof GotoStat)   // in a BB and detect a conditional branch
                {
                    // add the if statement into the BB
                    aBlock.addStatement(nodeToCheck);

                    // mark the block with the ending label name
                    aBlock.setEndLabel(nodeToCheck.GetNodeName());

                    // we also know one of its successors is the next immediate block
                    aBlock.addSuccessor("Block " + (bbCounter + 1));

                    // add the BB into funcCFG which holds the list of BB for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    // end of a BB
                    inBlock = false;
                    wasConditionalJump = true;
                    wasFunctionCall = false;

                } else if (nodeToCheck.getClass().getSimpleName().equals("ExprAst")
                        && nodeToCheck.GetNodeName().equals("FuncCall")) // in a BB, detect a function call within an assignment statement
                {
                    // the blocks successor is the next block
                    aBlock.addSuccessor("Block " + (bbCounter + 1));

                    // this statement would have already been added to the BB when it was detected as an assignment statement
                    // just need to end the block and insert it into the CFG for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    // BB ends
                    inBlock = false;

                    bbCounter++;
                    wasConditionalJump = false;
                    wasFunctionCall = true;

                } else if (nodeToCheck.getClass().getSimpleName().equals("ExprStat")) // in a BB detect a function call not in an assignment statement
                {

                    // add the statement into the BB
                    aBlock.addStatement(nodeToCheck);

                    // indicate its successor as the next BB
                    aBlock.addSuccessor("Block " + (bbCounter + 1));

                    // add the BB into the CFG for the current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;
                    wasConditionalJump = false;
                    wasFunctionCall = true;
                    inBlock = false;

                } else if (nodeToCheck instanceof ReturnStat) // in BB and detect a return statement
                {
                    // end the basic block
                    aBlock.addStatement(nodeToCheck);

                    aBlock.addSuccessor("Exit");

                    // add the block into the CFG for current function
                    funcCFG.addBasicBlock(aBlock);
                    aBlock = null;

                    bbCounter++;

                    inBlock = false;
                    wasConditionalJump = false;
                    wasFunctionCall = false;


                } else if (nodeToCheck instanceof AssignStat) {
                    // add the statement into the BB
                    aBlock.addStatement(nodeToCheck);
                    inBlock = true;
                }
            }


            // get the children so that it traverses the entire tree
            Enumeration children = nodeToCheck.GetChildren();
            int numChildren = nodeToCheck.GetNumChildren();

            for (int i = 1; i <= numChildren; i++) {
                AstNode tempChild = (AstNode) children.nextElement();
                treeRestack.push(tempChild);
            }

            while (!treeRestack.empty())
                treeStack.push(treeRestack.pop());
        }

        /* This is the end of building a CFG for the current function
         * now we have the information about how blocks end and begin.
         * We can determine the successors and predecessors of blocks that are
         * targeted with jumps and conditional branches
         */

        // determine the predecessors and successors for BB
        assignPredecessorsAndSuccessors(funcCFG);
    }

    /*
     * Assigns the predecessors and successors of a basic block based on the
     * start and end labels that a basic block contains. These successors and
     * predecessors are results of jumps and conditional branches.
     */

    public static void assignPredecessorsAndSuccessors(CFG currentCfg) {
        // look at the start label of each BB (focus block) and determine the other blocks that end with that start label
        // they are the predecessors of the focus block
        for (BasicBlock focusBlock : currentCfg.getFunctionCFG()) {
            if (focusBlock.getStartLabel() != null) {
                String entryLabel = focusBlock.getStartLabel();

                for (BasicBlock aBlock : currentCfg.getFunctionCFG()) {
                    if (aBlock.getEndLabel() != null && aBlock.getEndLabel().equals(entryLabel)) {
                        focusBlock.addPredecessor(aBlock.getBlockName());
                        aBlock.addSuccessor(focusBlock.getBlockName());
                    }
                }
            }
        }
    }


    /*
     * Traverses the tree and checks for return statement, array access, function call,
     * branch and assignment statements. When it finds one it calls the appropriate method
     * to transform the expressions according to the rules of Phase II
     */


    static void convertToAssembly(AstNode node) {

        Stack<AstNode> treeStack = new Stack<AstNode>();
        Stack<AstNode> treeRestack = new Stack<AstNode>();

        treeStack.push(node);

        // traverse the entire Abstract Syntax Tree
        while (!treeStack.empty()) {

            AstNode nodeToCheck = treeStack.pop();


            if (nodeToCheck.getClass().getSimpleName().equals("AssignStat")) {
                // assignment statement node found
                AssemblyBuilder.assignmentStatementCheck(nodeToCheck);
            } else if (nodeToCheck.getClass().getSimpleName().equals("ReturnStat")) {
                // return statement node found
                AssemblyBuilder.returnStatementCheck(nodeToCheck);
            } else if (nodeToCheck.getClass().getSimpleName().equals("GotoStat")) {
                // branch node found
                AssemblyBuilder.branchCondCheck(nodeToCheck);
            } else if (nodeToCheck.getClass().getSimpleName().equals("ArrayAcc")) {
                // array element access node found
                AssemblyBuilder.arrayIndexCheck(nodeToCheck);
            } else if (nodeToCheck.GetNodeName().equals("FuncCall")) {
                // function call node found
                AssemblyBuilder.functionCallCheck(nodeToCheck);
            }

            // get the children so that it traverses the entire tree
            Enumeration children = nodeToCheck.GetChildren();
            int numChildren = nodeToCheck.GetNumChildren();

            for (int i = 1; i <= numChildren; i++) {
                AstNode tempChild = (AstNode) children.nextElement();
                treeRestack.push(tempChild);
            }

            while (!treeRestack.empty())
                treeStack.push(treeRestack.pop());
        }
    }
}
