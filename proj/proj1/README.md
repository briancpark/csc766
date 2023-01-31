# Project 1 Phase I: Value Numbering

Brian Park

Here lies the README.md for the project. Feel free to view the Markdown file in a text editor or browser to properly
render the formatting. Otherwise, viewing the raw text should suffice.

To compile and run the code, it should be the commands below. There was nothing changed compared to the original starter
code.

```sh
make compile
make build
make run
```

The starter code in this repository will look different as it has been linted and reformatted.

To show the difference, here is the git diff

Below are the versions used. (on Mac)
java version "19" 2022-09-20
javac 19

If there is any error or issue with the setup, please contact [bcpark@ncsu.edu](mailto:bcpark@ncsu.edu).

### Instructions

You are provided with a template for the project. You can reuse the Makefile and the directory organization. Just add
your own files and make small changes to the Makefile. Type make, you'll complete build, compile and run in a batch way.
Check it out here and use it as a start point of your whole project. Read the README.txt in the package first! Machines
used for testing: the Linux systems in the NCSU VCL (use the "CSC 766 Code Optimization" image, which will be used in
our grading).

You must include a Readme file in which you should mention your name and the phase number first. Extra info should be
presented are

* file and directory descriptions
* building and running guide
* program descriptions, on structure and function
* descriptions of your optimizations
* bug reports

```
Executing automaton
Executing binrep
Executing fibonacci
Executing jacobi
Executing mandel
Executing movingedge
Executing multiply
Executing sort
Executing sort_insertion
Executing tax
Executing trivia
Instruction Count for automaton
instruction 41
Instruction Count for binrep
instruction 102
Instruction Count for fibonacci
instruction 1065
Instruction Count for jacobi
instruction 12550692
Instruction Count for mandel
instruction 5456926
Instruction Count for movingedge
instruction 7007
Instruction Count for multiply
instruction 2308
Instruction Count for sort
instruction 12836
Instruction Count for sort_insertion
instruction 2757
Instruction Count for tax
instruction 139
Instruction Count for trivia
instruction 59

```

```
echo Running the compiler on automaton to make automaton.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/automaton outputProgramDir/automaton.new.c cfgOutputDir/automaton.cfg
echo Compiling automaton.new.c with gcc
gcc outputProgramDir/automaton.new.c -o outputProgramDir/automaton
echo Running the compiler on binrep to make binrep.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/binrep outputProgramDir/binrep.new.c cfgOutputDir/binrep.cfg
echo Compiling binrep.new.c with gcc
gcc outputProgramDir/binrep.new.c -o outputProgramDir/binrep
echo Running the compiler on fibonacci to make fibonacci.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/fibonacci outputProgramDir/fibonacci.new.c cfgOutputDir/fibonacci.cfg
echo Compiling fibonacci.new.c with gcc
gcc outputProgramDir/fibonacci.new.c -o outputProgramDir/fibonacci
echo Running the compiler on jacobi to make jacobi.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/jacobi outputProgramDir/jacobi.new.c cfgOutputDir/jacobi.cfg
echo Compiling jacobi.new.c with gcc
gcc outputProgramDir/jacobi.new.c -o outputProgramDir/jacobi
echo Running the compiler on mandel to make mandel.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/mandel outputProgramDir/mandel.new.c cfgOutputDir/mandel.cfg
echo Compiling mandel.new.c with gcc
gcc outputProgramDir/mandel.new.c -o outputProgramDir/mandel
echo Running the compiler on movingedge to make movingedge.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/movingedge outputProgramDir/movingedge.new.c cfgOutputDir/movingedge.cfg
echo Compiling movingedge.new.c with gcc
gcc outputProgramDir/movingedge.new.c -o outputProgramDir/movingedge
echo Running the compiler on multiply to make multiply.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/multiply outputProgramDir/multiply.new.c cfgOutputDir/multiply.cfg
echo Compiling multiply.new.c with gcc
gcc outputProgramDir/multiply.new.c -o outputProgramDir/multiply
echo Running the compiler on sort to make sort.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/sort outputProgramDir/sort.new.c cfgOutputDir/sort.cfg
echo Compiling sort.new.c with gcc
gcc outputProgramDir/sort.new.c -o outputProgramDir/sort
echo Running the compiler on sort_insertion to make sort_insertion.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/sort_insertion outputProgramDir/sort_insertion.new.c cfgOutputDir/sort_insertion.cfg
echo Compiling sort_insertion.new.c with gcc
gcc outputProgramDir/sort_insertion.new.c -o outputProgramDir/sort_insertion
echo Running the compiler on tax to make tax.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/tax outputProgramDir/tax.new.c cfgOutputDir/tax.cfg
echo Compiling tax.new.c with gcc
gcc outputProgramDir/tax.new.c -o outputProgramDir/tax
echo Running the compiler on trivia to make trivia.new.c
java -ea -classpath ./AST_lib/src/:compilerClassRootDir/ package1.OptimizingCompiler inputProgramDir/trivia outputProgramDir/trivia.new.c cfgOutputDir/trivia.cfg
echo Compiling trivia.new.c with gcc
gcc outputProgramDir/trivia.new.c -o outputProgramDir/trivia

```

/Library/Java/JavaVirtualMachines/jdk-19.jdk/Contents/Home/bin/java -javaagent:/Users/briancpark/Library/Application\
Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.8617.56/IntelliJ\ IDEA.app/Contents/lib/idea_rt.jar=58908:
/Users/briancpark/Library/Application\ Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.8617.56/IntelliJ\
IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath
/Users/briancpark/dev/csc766/proj/proj1/out/production/proj1:/Users/briancpark/dev/csc766/proj/proj1/AST_lib/src:
/Users/briancpark/dev/csc766/proj/proj1/compilerClassRootDir package1.OptimizingCompiler inputProgramDir/automaton
outputProgramDir/automaton.new.c cfgOutputDir/automaton.cfg