package package1;

import ast.parser.*;

public class AstBuilder {

    public static VarAst declareVariable(String varName, String primType, int indirection) {
        // indicate variable primitive type;
        DataType dType = new DataType(primType, 0);

        // join the type with the name of the variable to create a declaration
        VarAst varDecNode = new VarAst(varName, dType);

        return varDecNode;
    }

    public static VarAst declareMethod(String funcName, String primType, String returnType, int indirection) {
        DataType dTypeReturn = new DataType(returnType, indirection);

        FuncType fType = new FuncType(dTypeReturn);

        DataType dTypePrimitive = new DataType(primType, indirection);

        VarAst fDeclaration = new VarAst(funcName, dTypePrimitive);

        fType.InsertMeAsChildOf(dTypePrimitive, 0);

        return fDeclaration;
    }

    public static ExprStat createFunctionCall(String methodName) {
        VarAccAst fName = new VarAccAst(methodName);

        ExprStat fCall = new ExprStat("FuncCall");

        fName.InsertMeAsChildOf(fCall, 0);

        return fCall;
    }
}