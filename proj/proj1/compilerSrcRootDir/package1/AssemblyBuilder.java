package package1;

import ast.parser.*;

import java.util.Enumeration;

public class AssemblyBuilder {

    private static int tempCounter;

    /**
     * Checks that the right hand side of an assignment statement has at most
     * one operator with two operands. It also transforms the operands into
     * either a variable or a constant.
     ***/

    public static void assignmentStatementCheck(AstNode nodeToCheck) {

        // get the two children of the assignment '=' node
        Enumeration assignmentChildren = nodeToCheck.GetChildren();

        AstNode leftAssignChild = (AstNode) assignmentChildren.nextElement();
        AstNode rightAssignChild = (AstNode) assignmentChildren.nextElement();

        // check if the right child is a node of type ExprAst, this is
        // an arithmetic operation

        if (rightAssignChild.getClass().getSimpleName().equals("ExprAst")) {

            // it is ok to have an expression with two operands,
            // but if this node has another operand as a child
            // then there are too many operands in this assignment expression

            int numExprChildren = rightAssignChild.GetNumChildren();
            Enumeration exprChildren = rightAssignChild.GetChildren();

            for (int i = 0; i < numExprChildren; i++) {

                AstNode exprSubChild = (AstNode) exprChildren.nextElement();

                // if more operators found in the assignment then have to
                // reassign them to temp values

                if (exprSubChild.getClass().getSimpleName().equals("ExprAst") ||
                        exprSubChild.getClass().getSimpleName().equals("ArrayAcc")) {

                    // transform the found code and return with a temp variable
                    AstNode tempHold = assignToTemp(exprSubChild, nodeToCheck);

                    // insert the temp variable inplace of the transformed code
                    tempHold.InsertMeAsChildOf(rightAssignChild, i);
                }
            }
        }
    }


    /**
     * Checks if function calls have complex arguments that should be
     * evaluated and reassigned to temp values.
     ***/

    static void functionCallCheck(AstNode nodeToCheck) {

        // a function call can either be part of an assignment operation
        // or just a function call that does not return anything!!

        // the kind of function call depends depends on the
        // class name of FuncCall node. It can either be a node of class
        // ExprAst or ExpsStat.

        AstNode firstLevelNode = null;

        // determine the appropriate firstLevelNode that temp variable
        // calculation and assignment can be attached to so that it is evaluated
        // before it is needed

        if (nodeToCheck.getClass().getSimpleName().equals("ExprAst")) {
            // this is a func call that is part of an assignment
            // have to traverese up and find the assignStat node.

            firstLevelNode = nodeToCheck.GetParentAst();

            // find the assignStat node
            while (!firstLevelNode.getClass().getSimpleName().equals("AssignStat"))
                firstLevelNode = firstLevelNode.GetParentAst();

        } else if (nodeToCheck.getClass().getSimpleName().equals("ExprStat")) {

            // this func call is not part of an assignment statement
            firstLevelNode = nodeToCheck;
        }

        int numArgs = nodeToCheck.GetNumChildren();
        Enumeration funcCallArguments = nodeToCheck.GetChildren();

        // check the arguments of the function call, they should be either constants
        // or variables
        for (int child = 0; child < numArgs; child++) {

            AstNode argument = (AstNode) funcCallArguments.nextElement();

            if (argument.getClass().getSimpleName().equals("ExprAst") ||
                    argument.getClass().getSimpleName().equals("ArrayAcc")) {

                // transform the found code and return with a temp variable
                AstNode tempHold = assignToTemp(argument, firstLevelNode);

                // insert the temp variable inplace of the transformed code
                tempHold.InsertMeAsChildOf(nodeToCheck, child);
            }
        }
    }


    /**
     * Checks if the conditions in the branches are too complex. This means they are not
     * variables or constants but expressions and other statements.
     ***/

    static void branchCondCheck(AstNode nodeToCheck) {

        int numGoToChildren = nodeToCheck.GetNumChildren();

        // Check that this goto is a coditional branch so it must have children
        if (numGoToChildren != 0) {

            Enumeration goToChildren = nodeToCheck.GetChildren();
            AstNode goToChild = (AstNode) goToChildren.nextElement();

            // the comparison node has two children both of which could be long
            // expressions or function calls we have to make them into temp variables

            Enumeration compChildren = goToChild.GetChildren();

            AstNode leftCompChild = (AstNode) compChildren.nextElement();
            AstNode rightCompChild = (AstNode) compChildren.nextElement();

            // check the expression to the left of the comparison operator
            if (leftCompChild.getClass().getSimpleName().equals("ExprAst") ||
                    leftCompChild.getClass().getSimpleName().equals("ArrayAcc")) {

                // transform the found code and return with a temp variable
                AstNode tempHold = assignToTemp(leftCompChild, nodeToCheck);

                // insert the temp variable inplace of the transformed code
                tempHold.InsertMeAsChildOf(goToChild, 0);
            }

            // check the expression to the right of the comparison operator
            if (rightCompChild.getClass().getSimpleName().equals("ExprAst") ||
                    rightCompChild.getClass().getSimpleName().equals("ArrayAcc")) {

                // transform the found code and return with a temp variable
                AstNode tempHold = assignToTemp(rightCompChild, nodeToCheck);

                // insert the temp variable inplace of the transformed code
                tempHold.InsertMeAsChildOf(goToChild, 1);
            }
        }
    }


    /**
     * Checks that the index to the array element access is not a complex expression.
     * The index should be a single variable or constant.
     ***/

    static void arrayIndexCheck(AstNode nodeToCheck) {

        // now check if the index of the array is a ConstAst or VarAccAst
        // have to get its children

        Enumeration arrayChildren = nodeToCheck.GetChildren();

        AstNode leftArrayChild = (AstNode) arrayChildren.nextElement();
        AstNode rightArrayChild = (AstNode) arrayChildren.nextElement();

        // the right child of an array access node is the index
        // check if it is not just a variable or a constant

        if (rightArrayChild.getClass().getSimpleName().equals("ExprAst")) {

            // transform the found code and return with a temp variable
            AstNode tempHold = assignToTemp(rightArrayChild, nodeToCheck.GetParentAst());

            // insert the temp variable inplace of the transformed code
            tempHold.InsertMeAsChildOf(nodeToCheck, 1);

        }
    }


    /**
     * Checks if the return statement contains more than just a single variable
     * or constant. If it a complex expression it transforms the code.
     ***/

    static void returnStatementCheck(AstNode nodeToCheck) {

        // a return statement has only one child

        Enumeration returnChildren = nodeToCheck.GetChildren();
        AstNode returnChild = (AstNode) returnChildren.nextElement();

        // check if the return statement contains a function call,
        // array element index, or an expression

        if (returnChild.getClass().getSimpleName().equals("ExprAst") ||
                returnChild.getClass().getSimpleName().equals("ArrayAcc")) {

            // transform the found code and return with a temp variable
            AstNode tempHold = assignToTemp(returnChild, nodeToCheck);

            // insert the temp variable inplace of the transformed code
            tempHold.InsertMeAsChildOf(nodeToCheck, 0);
        }
    }


    /**
     * Takes the received node and assigns its evaluated value to a temporary variable.
     * Also checks the children of the received node and assigns their evaluated
     * values to other temporary variables. The temporary variable assignment statements
     * are inserted into the tree so that their values are assigned before they are
     * referenced.
     * <p>
     * Performs type checking and declares temp_x variables of the correct type, int/float
     ***/

    static AstNode assignToTemp(AstNode toTemp, AstNode firstLevelNode) {

        // check the children of the node we are dealing with, it may have children that
        // need to be moved up

        int numChildren = toTemp.GetNumChildren();

        Enumeration children = toTemp.GetChildren();

        for (int child = 0; child < numChildren; child++) {

            AstNode tempChild = (AstNode) children.nextElement();

            if (tempChild.getClass().getSimpleName().equals("ExprAst") ||
                    tempChild.getClass().getSimpleName().equals("ArrayAcc")) {

                AstNode tempHold = assignToTemp(tempChild, firstLevelNode);
                tempHold.InsertMeAsChildOf(toTemp, child);
            }
        }


        // determine the type to make the temp_x variable based on the
        // 1) type of the array on the right hand side of the assignment to temp_x,
        // 2) or based on the return type of the function being called on the
        //    right hand side of the assignment to the temp,
        // 3) or based on the variables in the expression on the right hand side
        //    of the assignment to the temp
        boolean isInt = true;


        if (toTemp.GetNodeName().equals("FuncCall")) {
            // there is a function call that will be assigned to temp_x

            Enumeration funcCallChildren = toTemp.GetChildren();
            AstNode funcCallChild = (AstNode) funcCallChildren.nextElement();

            // perform a look up in the symbol table by the name of the array
            VarAst varAstNode = funcCallChild.SearchSymbolTable(funcCallChild.GetNodeName());


            // from the symbol table we receive a VarAst node that was used in the
            // declaration of the array, have to dig down its
            // children to get to the dataType node that stores the type
            Enumeration dType = varAstNode.GetChildren();
            AstNode dTypeChild = (AstNode) dType.nextElement();

            Enumeration funcType = dTypeChild.GetChildren();
            AstNode funcTypeChild = (AstNode) funcType.nextElement();

            Enumeration primType = funcTypeChild.GetChildren();
            AstNode primTypeFinal = (AstNode) primType.nextElement();

            // check the node name of the dataType node it is either int or float
            isInt = primTypeFinal.GetNodeName().equals("int");

        } else if (toTemp.getClass().getSimpleName().equals("ArrayAcc")) {
            // there is an array access that will be assigned to temp_x

            Enumeration arrayChildren = toTemp.GetChildren();
            AstNode arrayName = (AstNode) arrayChildren.nextElement();

            // use the name of the array for look up in the symbol table
            VarAst varAstNode = arrayName.SearchSymbolTable(arrayName.GetNodeName());

            // dig down to the dataType node that contains the array type
            Enumeration dType = varAstNode.GetChildren();
            AstNode dTypeChild = (AstNode) dType.nextElement();

            Enumeration arrayType = dTypeChild.GetChildren();
            AstNode arrayTypeChild = (AstNode) arrayType.nextElement();

            Enumeration primType = arrayTypeChild.GetChildren();
            AstNode primTypeFinal = (AstNode) primType.nextElement();

            // check the node name of the dataType node it is either int or float
            isInt = primTypeFinal.GetNodeName().equals("int");

        } else if (toTemp.getClass().getSimpleName().equals("ExprAst")) {
            // variables and constants are assigned to temp_x

            Enumeration operandChildren = toTemp.GetChildren();

            // an operator has two operands, maybe both variables,
            // maybe a mix of constant and variable
            AstNode operandChildOne = (AstNode) operandChildren.nextElement();
            AstNode operandChildTwo = (AstNode) operandChildren.nextElement();

            VarAst varAstOne = operandChildOne.SearchSymbolTable(operandChildOne.GetNodeName());
            VarAst varAstTwo = operandChildTwo.SearchSymbolTable(operandChildTwo.GetNodeName());

            // a constant  will not be found in the symbol table
            // so it will not have a reference to a dataType

            // check if the left child is a variable
            if (operandChildOne.getClass().getSimpleName().equals("VarAccAst")) {

                Enumeration childOne = varAstOne.GetChildren();
                AstNode childOnePrim = (AstNode) childOne.nextElement();

                // check the node name of the dataType node it is either int or float
                isInt = childOnePrim.GetNodeName().equals("int");
            }

            // check if the right child is a variable
            if (operandChildTwo.getClass().getSimpleName().equals("VarAccAst")) {

                Enumeration childTwo = varAstTwo.GetChildren();
                AstNode childTwoPrim = (AstNode) childTwo.nextElement();

                // check the node name of the dataType node it is either int or float
                isInt = childTwoPrim.GetNodeName().equals("int");
            }

        }


        DataType tempDataType;

        if (isInt) {
            // define and declare the temp variable as int
            tempDataType = new DataType("int", 0);

        } else {
            // the only other type is float
            tempDataType = new DataType("float", 0);
        }

        VarAst tempVarAst = new VarAst("temp_" + tempCounter, tempDataType);

        // insert the declaration at the firstLevelNode level
        tempVarAst.InsertMeBefore(firstLevelNode);

        // node to access the name of the temp variable
        VarAccAst tempAccess = new VarAccAst("temp_" + tempCounter);

        AssignStat assignToTemp = new AssignStat("=");

        // create 'temp_x = toTemp'
        tempAccess.InsertMeAsChildOf(assignToTemp, 0);

        // detach from original tree and place under assignment to temp_x
        toTemp.MoveMeAsChildOf(assignToTemp, 1);

        // take the 'temp = toTemp' tree and attach it at the firstLevelNode level
        assignToTemp.InsertMeBefore(firstLevelNode);

        // need to be able to access the temp_x variable again
        VarAccAst tempAccessAgain = new VarAccAst("temp_" + tempCounter);

        tempCounter++;

        return tempAccessAgain;
        // this returned node has to be attached to where the toTemp was removed
    }
}
	
	
	
	
