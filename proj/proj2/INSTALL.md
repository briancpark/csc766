********************

* README           *

********************

This folder contains a basic compiler. The main function dumps the
CFG's of each of a batch of input programs into a text file with
extension .cfg (for instance, automaton.cfg for automaton.new.c) in
the output program directory. The output text file contains the
control flow graph of the input program.

* OUTPUT

--------
A CFG for each progam in the inputProgramDir folder resides in the
directory cfgOutputDir/.


DIRECTORIES
-----------

compilerSrcRootDir/ : Contains the package1 directory that
contains ".java" files that compose the compiler.

compilerClassRootDir/ : Contains the package1 directory that
contains ".class" files that compromise the compiler.

inputProgramDir/ : Contains the test programs in an intermediate
representation. The files are ".adap" files and they are read in by the
compiler and an abstract syntax tree is created from them.

outputProgramDir/ : Contains the compiled .c files of the test programs
and their executables.

programInput/ : Contains text files that contain the input that is used
on each test program when it is executed in a batched manner.

cfgOutputDir/ : Contains the ".cfg" files for each program. These
files are the control flow graphs of the programs.


FILES
-----

Makefile :

a script that is used to compile the compiler and run the
executables with batched input from the programInput directory.

OptimizingCompiler.java :

contains the main() function.

TrivialSetup.java :

This file implements a facility to help count the number of
instructions in an execution of a program. You may ignore it if you
don't want to count dynamic instructions (which is not required). It
includes methods that insert the calls to init() at the beginning of a
program, report() at the exit of the program, and recordInst() before
each statement (except return statements) in the program. The
functions maintain a global counter that records the number of dynamic
instructions (i.e. instructions executed) in the program. An example
of instrumented programs (without the implementation of the functions)
is in the folder named instrumented_example.

AssemblyBuilder.java :

This file implements methods to transform the program into an
assembly-like format. It works on the AST. After the transformation,
you may call method GenCode() to generate a corresponding C file.
The generated expressions satisfy the following format:

* Left hand side of an assignment statement should be a variable or an
  array access.
* Right hand side of an assignment statement should be either a
  variable, a constant, an array access, a function call or an
  expression with no more than two operands (or one operator).
* Any operand of an expression can only be a variable or a constant.
* The predicate of a conditional jump can be one comparison operation
  whose two operands are either a variable or a constant.
* Parameters to a function call can only be a variable or a constant.
  Index of an array can only be a variable or a constant.
* Return parameter can only be a variable or a constant.

For example, for the following code:
a = b + c[e] + d;
if (a+d<b) goto L1;
foo (m[a+d]);
m[e+f] = m[a] + m[d];
L1:
the generated assembly-like code looks like:
t0 = c[e];
t1 = b + t0;
a = t1 + d;
t2 = a + d;
if (t2 < b) goto L1;
t3 = a + d;
t4 = m[t3];
foo(t4);
t5 = m[a];
t6 = m[d];
t7 = e + f;
m[t7] = t5 + t6;
L1:

AstBuilder.java :

A helping class that provides methods that simplifies the creation of
certain AST nodes. Used for AssemblyBuilder.

BasicBlock.java :

The class BasicBlock is the blue print for type BasicBlock. It represents a
basic block. A basic black contains a list of predecessors, successors
and statements that are in the basic block.

CFG.java :

Used in constructing a control flow graph (CFG) for each function
definition. It identifies all the basic blocks (BB) in a function and
figure out predecessors and successors of each BB. This CFG class
represents a Control Flow Graph. It contains a field that is the name
of the function that this is a CFG for. It also contains a list of
basic blocks that are in this Control Flow Graph.

* BUILDING AND RUNNING GUIDE

----------------------------
Requires Java 1.5 or later versions

Once you have untared the .tar file and are inside the directory where
the Makefile resides, there are three commands you have to type in the
following order at the command line to run the compiler and obtain the
instruction counts:


First command: make build
-------------------------
This will compile the compiler.


Second command: make compile
----------------------------

This will compile the .adap (intermediate representation) into .c
files using the compiler. Then the .c files will be compiled into
executables using gcc.

You may see a sequence of WARNINGS print for each program by gcc. You can
safely ignore them.


Third command: make run
-----------------------

This will execute the executables and input will be batched
into the executables so they are not interactive. Instruction counts
will print for each program.


Optional Command: make clean
----------------------------

This will remove all files from the following directories:

cfgOutputDir/
outputProgramDir/
compilerClassRootDir/

All ".cfg" , ".new.c" , ".class" and c languge executables will be deleted.

* PROGRAM STRUCTURE DESCRIPTION

-------------------------------

The compiler reads in the intermediate representation which is the
.adap files. Once it reads in the intermediate representation it
constructs an Abstract Syntax Tree.

The AST is another intermediate representation which will be converted
into c code that is then compiled by gcc into executables.

The inputProgramDir directory contains the .adap files which are the
first intermediate representation. The outputProgramDir directory
contains the .c code and the executables that are generated by gcc.

* PROGRAM FUNCTION DESCRIPTION

------------------------------
The file OptimizingCompiler.java contains a main() method that reads
in the .adap intermediate representation and builds an AST.

OptimizingCompiler.constructCFG() :

Locate a function definition in the AST, pass the node for the root of
the function to the createFunctionCFG() method to create a CFG for the
function.

OptimizingCompiler.constructFunctionCFG() :

Receives a node that is the root of a function definition in the AST
creates a CFG for the function.

OptimizingCompiler.assignPredecessorsAndSuccessors() :

Assigns the predecessors and successors of a basic block based on the
start and end labels that a basic block contains. These successors and
predecessors are results of jumps and conditional branches.

OptimizingCompiler.convertToAssembly() :

Traverses the tree and checks for return
statement, array access, function call, branch and assignment
statements. When it finds one it calls the appropriate method to
transform the expressions according to the aforementioned rules.

AssemblyBuilder.assignmentStatementCheck() :

Checks that the right hand side of an assignment statement has at most
one operator with two operands. It also transforms the operands into
either a variable or a constant.

AssemblyBuilder.functionCallCheck() :

Checks if function calls have complex arguments
that should be evaluated and reassigned to temp values.

AssemblyBuilder.branchCondCheck() :

Checks if the conditions in the branches are too
complex. This means they are not variables or constants but
expressions and other statements.

AssemblyBuilder.arrayIndexCheck() :

Checks that the index to the array element access is not a complex
expression. The index should be a single variable or constant.

AssemblyBuilder.returnStatementCheck() :

Checks if the return statement contains more than just a single
variable or constant. If it a complex expression it transforms the
code.

AssemblyBuilder.assignToTemp() :

Takes the received node and assigns its evaluated value to a temp_x
variable. Also checks the children of the received node and assigns
their evaluated values to temp_x variables. The temp_x variable
assignment statements are inserted into the tree so that their values
are assigned before they are referenced. Also performs type checks and
declares a temp_x variable of the right type.

TrivialSetup.defineDeclareTrivialMethod() :

Declares the global counter variable int counter in the C progam. It
also declares and defines the methods init(), recordInst(), and
report() in each C progam.

TrivialSetup.insertInitCall() :

Inserts a call to the method init() as the first statement in the
method main() of the C program. It also calls the method
insertReportCall() to insert the report() method call into the C program.

TrivialSetup.insertReportCall() :

Inserts a call to the method report() before the return statement
inside main() of the C program to return the instruction count for
this execution of the C program.

TrivialSetup.insertRecordInstCall() :

Inserts a call to the method recordInst() before any assignment, goto
or expression statement to increment the global instruction counter
for executed instructions in this run of the C progam.

TrivialSetup.printTree() :

Prints the AST tree.

TrivialSetup.recursivePrintTree() :

Prints the AST tree recursively.

