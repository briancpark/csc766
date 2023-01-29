package package1;

import java.util.*; 

public class CFG{
	
	// indicates which function this is a CFG for
	private String functionName;
	
	// the list of basic blocks that make up this CFG
	private List <BasicBlock> controlFlow;
	
	// constructor
	public CFG(String name)
	{
		functionName = name;
		controlFlow = new ArrayList <BasicBlock>();
	}
	
	
	// get methods
	public String getFunctionName()
	{
		return functionName;
	}
	
	public List<BasicBlock> getFunctionCFG()
	{
		return controlFlow;
	}
	
	
	// set methods
	public void setFunctionName( String fName )
	{
		functionName = fName ;
	}
	
	
	public void addBasicBlock( BasicBlock b)
	{
		this.controlFlow.add(b);
	}
	
	
	public String toString()
	{
		return "*** CFG for function: " + functionName + "***\n";
	}

}
