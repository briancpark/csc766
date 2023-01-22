package package1;

import java.util.*;
import ast.parser.*;

public class TrivialSetup {

	/**
	 *
	 * Declares the global counter variable 'counter',
	 * Declares and defines the methods init(), recordInst(), and report()
	 *     
	 ****************/

	public static void defineDeclareTrivialMethods(AstNode node) throws AssertionError 
	{
		Enumeration children = node.GetChildren();

		AstNode fileNode = (AstNode)children.nextElement();

 
		// declare the counter variable
		VarAst varDecNode = AstBuilder.declareVariable( "counter", "int", 0 );

		// insert the counter variable into the AST
		varDecNode.InsertMeAsChildOf( fileNode, 0 );


		// declare the init() method
		VarAst initDeclaration = AstBuilder.declareMethod( "init", "function", "void", 0 );

		// insert the init() method declaration into the AST
		initDeclaration.InsertMeAsChildOf( fileNode, 1 );


		/* define init() */

		DataType init_def_dType = new DataType( "void", 0 );

		FuncType init_def_FuncType = new FuncType( init_def_dType );

		CodeBlock init_def_CodeBlock = new CodeBlock( "initBody" );


		/* init() method's code block */

		// initialize value of 'counter' to 0 

		ConstAst counterValue = new ConstAst( "0" );

		VarAccAst counterName = new VarAccAst( "counter" );

		AssignStat counterAssign = new AssignStat( "=" );

		counterName.InsertMeAsChildOf( counterAssign, 0 );

		counterValue.InsertMeAsChildOf( counterAssign, 1 );

		// insert the statement 'int counter = 0' into the code block
		counterAssign.InsertMeAsChildOf( init_def_CodeBlock, 0 );


		// attach the code block to the init() method 
		FuncAst init_def_FuncAst = new FuncAst( "init", StorageClass.AUTO, init_def_FuncType, init_def_CodeBlock );

		// insert the init() method definition into the AST
		init_def_FuncAst.InsertMeAsChildOf( fileNode, 2 );


		// declare recordInst()
		VarAst recordInstDeclaration = AstBuilder.declareMethod( "recordInst", "function", "void", 0 );

		// insert the recordInst() declaration into the AST 
		recordInstDeclaration.InsertMeAsChildOf( fileNode, 3 );


		/* define recordInst() */

		DataType recordInst_def_dType = new DataType( "void", 0 );

		// begin defining recordInst()
		FuncType recordInst_def_funcType = new FuncType( recordInst_def_dType );

		CodeBlock recordInst_codeBlock = new CodeBlock( "recordInstBody" );


		/* recordInst() method's code block */

		// create the statement 'counter = counter + 1';
		ConstAst valueOfOne = new ConstAst( "1" );

		VarAccAst counterName2 = new VarAccAst( "counter" );

		VarAccAst counterName3 = new VarAccAst( "counter" );

		ExprAst plusOp = new ExprAst( "+" );

		AssignStat counterAssign2 = new AssignStat( "=" );

		counterName2.InsertMeAsChildOf( counterAssign2, 0 );

		plusOp.InsertMeAsChildOf( counterAssign2, 1 );

		counterName3.InsertMeAsChildOf( plusOp, 0 );

		valueOfOne.InsertMeAsChildOf( plusOp, 1 );

		// insert the statement 'counter = counter + 1' into the code block
		counterAssign2.InsertMeAsChildOf( recordInst_codeBlock, 0 );

		// attach the code block to the definition of the recordInst() method
		FuncAst recordInst_definition = new FuncAst( "recordInst", StorageClass.AUTO, recordInst_def_funcType, recordInst_codeBlock);	

		// attach the recordInst() method declaration node to the AST
		recordInst_definition.InsertMeAsChildOf( fileNode, 4 );


		// declare report()
		VarAst reportDeclaration = AstBuilder.declareMethod( "report", "function", "void", 0 );

		// insert the report() method declaration into the AST
		reportDeclaration.InsertMeAsChildOf( fileNode, 5 );


		/* define report()*/

		DataType report_def_dType = new DataType( "void", 0 );

		FuncType report_def_ft = new FuncType( report_def_dType );

		CodeBlock report_CodeBlock = new CodeBlock( "reportBody" );


		/* report() code block */

		// create the statement 'printf("%d", counter);'

		VarAccAst counterName4 = new VarAccAst( "counter" );

		ConstAst reportFormat = new ConstAst( "\"instruction %d\"" );

		VarAccAst printfName = new VarAccAst( "printf" );

		ExprStat printfCall = new ExprStat( "FuncCall" );

		printfName.InsertMeAsChildOf( printfCall, 0 );

		reportFormat.InsertMeAsChildOf( printfCall, 1 );

		counterName4.InsertMeAsChildOf( printfCall, 2 );

		// insert statement into the code block
		printfCall.InsertMeAsChildOf( report_CodeBlock );


		// insert the code block into report() method definition
		FuncAst report_definition = new FuncAst( "report", StorageClass.AUTO, report_def_ft, report_CodeBlock );

		// insert report() definition into the AST
		report_definition.InsertMeAsChildOf( fileNode, 6 );
	}


	/**
	 *
	 * Inserts a call to the method init() as the first statement in
	 * the method main()
	 *
	 * Also calls the method insertReportCall() to insert the report() method
	 * call
	 *     
	 ****************/
	static void insertInitCall(AstNode node){

		// using a stack to traverse the AST
		Stack <AstNode> treeStack = new Stack <AstNode>();

		treeStack.push(node);

		while(!treeStack.isEmpty()){

			AstNode temp = treeStack.pop();

			// searching for the main() method definition

			if( temp instanceof FuncAst && temp.GetNumChildren() != 0 && temp.GetNodeName().equals("main") )
			{
				// found the main() method definition, need to find the 1st code block inside of main()


				Enumeration mainChildren = temp.GetChildren();
				AstNode child = (AstNode)mainChildren.nextElement();

				while( !( child.getClass().getSimpleName().equals( "CodeBlock" ) ) )
					child = (AstNode)mainChildren.nextElement();

				// child is the node that is the 1st code block of main()

				// insert the call to init() as the first child of the code block node

				// init() call statement creation
				ExprStat initFunctionCall = AstBuilder.createFunctionCall("init");

				// attach the call to init() as the first child of the code block
				initFunctionCall.InsertMeAsChildOf( child, 0 );


				// while inside of main we can insert the call to the report() method. 
				// Have to find the return statement in the body of main() 

				insertReportCall(temp);
			}


			Enumeration children = temp.GetChildren();

			for(int i = 1; i <= temp.GetNumChildren(); i++){

				AstNode tempChild = (AstNode)children.nextElement();

				treeStack.push(tempChild);		
			}
		}
	}

	
	/**
	 *
	 * Inserts a call to the method report() before the return statement 
	 * inside main()
	 *     
	 ****************/
	static void insertReportCall(AstNode node){

		// to traverse the AST use a stack to push and pop nodes
		Stack< AstNode > treeStack = new Stack< AstNode >();

		treeStack.push(node);

		while(!treeStack.isEmpty()){

			AstNode temp = treeStack.pop();

			// searching for the return statement inside of main()
			if(temp.getClass().getSimpleName().equals("ReturnStat")){

				// return statement found
				// temp is the node for the return statement

				// create a call to the report() function
				ExprStat reportCall = AstBuilder.createFunctionCall("report");

				// insert the call to report() right before the return statement
				reportCall.InsertMeBefore(temp);
			}

			Enumeration children = temp.GetChildren();

			for( int i = 1; i <= temp.GetNumChildren(); i++ ){

				AstNode tempChild = (AstNode)children.nextElement();

				treeStack.push(tempChild);		
			}
		}
	}


	/**
	 *  
	 * Inserts a call to the method recordInst() before any
	 * assignment, goto or expression statement. 
	 *     
	 ****************/

	static void insertRecordInstCall(AstNode node){

		// Traverse the AST by using a stack to push and pop the nodes
		Stack< AstNode > treeStack = new Stack< AstNode >();

		treeStack.push(node);

		while(!treeStack.isEmpty()){

			AstNode temp = treeStack.pop();

			// insert a call to the recordInst() method only before
			// assignment, goto and expression statements

			if(temp instanceof AssignStat 
					|| temp instanceof GotoStat 
					|| temp instanceof ExprStat){


				/*** create a recordInst() call statement  ***/
				VarAccAst recordInstName = new VarAccAst("recordInst");

				ExprStat recordInstCall = new ExprStat("FuncCall");

				recordInstName.InsertMeAsChildOf(recordInstCall);

				// insert the recordInst() method call before the found
				// assignment, goto or expression statement

				recordInstCall.InsertMeBefore(temp);

			}

			Enumeration children = temp.GetChildren();

			for(int i = 1; i <= temp.GetNumChildren(); i++){

				AstNode tempChild = (AstNode)children.nextElement();

				treeStack.push(tempChild);		
			}
		}
	}


	/**
	 * 
	 *  This method just prints the AST in a readable fashion 
	 *  
	 ****************/
	static void printTree(AstNode node) throws AssertionError {

		Stack <AstNode> treeStack = new Stack <AstNode>();
		Stack <AstNode> treeRestack = new Stack <AstNode>();

		treeRestack.push(node);

		while(!treeRestack.isEmpty()){

			AstNode temp = treeRestack.pop();

			System.out.println("PARENT: " + temp.GetNodeName());
			System.out.println("adap: " + temp.getClass().getSimpleName());

			//System.out.println("# Children: " + temp.GetNumChildren());

			Enumeration children = temp.GetChildren();

			for(int i = 1; i <= temp.GetNumChildren(); i++){

				AstNode tempChild = (AstNode)children.nextElement();
				System.out.println("      CHILD " + i + ": " + tempChild.GetNodeName());
				treeStack.push(tempChild);		
			}
			System.out.println();

			while(!treeStack.empty())
				treeRestack.push(treeStack.pop());	
		}			
	}
	
	
	/**
	 * 
	 * Traverses and prints the AST recursively
	 * 
	 ****************/
	protected static void recursivePrintTree(AstNode node) throws AssertionError
	{
		int numChildren = node.GetNumChildren();
		Enumeration children = node.GetChildren();
		
		// base case
		if(numChildren == 0)
		{
			System.out.println( "Parent: " + node.GetNodeName() );
			System.out.println( "adap:" + node.getClass().getSimpleName() );
		}
		else
		{
			System.out.println( "Parent: " + node.GetNodeName() );
			System.out.println( "adap:" + node.getClass().getSimpleName() );
			
			for( int k = 1; k <= numChildren; k++ )
			{
				AstNode child = (AstNode)children.nextElement();
				System.out.println( "    CHILD " + k + ": "+ child.GetNodeName() );
				//recursivePrintTree(child);
			}
			
			Enumeration childrenAgain = node.GetChildren();
			
			for( int k = 1; k <= numChildren; k++ )
			{
				AstNode child = (AstNode)childrenAgain.nextElement();
				recursivePrintTree(child);
			}
		}
	}
}
