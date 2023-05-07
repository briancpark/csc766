# Project 1 Phase I: Value Numbering

Brian Park

Here lies the README.md for the project. Feel free to view the Markdown file in a text editor or browser to properly
render the formatting. Otherwise, viewing the raw text should suffice.

## Building and Running the Project

To compile and run the code, it should be the commands below. There was nothing changed compared to the original starter
code.

```sh
make compile -j
make build -j
make run
```

Note that on VCL, you may need to install `javac`:

```sh
sudo apt install default-jdk -y
```

## File and Directory Descriptions

The starter code in this repository will look different as it has been linted and reformatted. To show the difference,
here is the `git diff`:

```diff
briancpark@Brians-MBP proj1 % git diff --stat ce1412e294166dba5f6eb3
 proj/proj1/README.md                                           | 204 ++++++++++++++++++++++++++++++++------------------------------------
 proj/proj1/compilerSrcRootDir/package1/BasicBlock.java         | 134 ++++++++++++++++++++++++++++++++++++++++++++-
 proj/proj1/compilerSrcRootDir/package1/OptimizingCompiler.java |  19 +++++++
 proj/proj1/proj1A.tar                                          | Bin 0 -> 610243 bytes
```

## Design Document

The codebase was modified to add support for redundancy elimination via value numbering on the control flow graph. To
accomplish this, I added a subroutine `valueNumbering(prog)` to the `OptimizingCompiler` class. This subroutine first
computes the two hash tables required for value numbering, `valueNumberTable` and `rewrittenTable`. This was only done
at a basic block level, so I added two more functions, `valueNumbering` and `rewrite` to the `BasicBlock` class. To
implement value numbering at a large scope (super value numbering), I would've added a subroutine to the `CFG` class
that inherited the hash tables from the basic blocks and then performed the value numbering on the entire graph. But
that was not the main goal of this assignment, so I did not implement it.

## Optimizations: Value Numbering

The value numbering is implemented with parsing the basic block's statement and expressions. I had to ignore expression
ASTs with functions, as that gets messy with variable length children, as functions can pass multiple arguments. Why
are functions ignored? I made that decision since you can call the same function with same arguments, but it may have
different effect if it has access to global variables or a different stack frame. Thus, I decided to ignore functions
for the sake of simplicity. On the other hand, I accounted for expressions and arrays. Arrays have a special case, as
they are not considered to be the same if they have different indices. Simply, I used the array AST type to my advantage
and distinguished arrays apart from their indices by treating array + offset as a unique expression.

The logic for value numbering is actually quite messy, as I had to account for the different types of ASTs. But since
the compiler is optimizing the code for runtime, I sacrificed the efficiency of the code for readability and precise
logic.

Note that this optimization may have no effect on certain programs, due to the size of each basic block. For example,
automaton had no rewritten values, because the basic blocks were too small due to function calls and branching. Jacobi
program had a lot of rewritten values, because the basic blocks were large and had a lot of arithmetic operations.

As a result, there were no changes in instructions because only redundant variables were eliminated. But the number of
instructions defined by this project are defined by the lines of C code. So actually, elimiating redundant expressions
should reduce the number of instructions executed on the ALU as well as reduce the usage of registers that would incur
register spilling.

## Debugging and Other Notes

This is some other notes included for my own sanity and development.

### Java Version

Below are the versions used on macOS (Apple Silicon):

```sh
% java -version 
java version "19" 2022-09-20
Java(TM) SE Runtime Environment (build 19+36-2238)
Java HotSpot(TM) 64-Bit Server VM (build 19+36-2238, mixed mode, sharing)

% javac -version
javac 19

% gcc --version
gcc (Homebrew GCC 12.2.0) 12.2.0
Copyright (C) 2022 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
```

On VCL, the versions are as follows:

```sh
$ java -version
openjdk version "11.0.17" 2022-10-18
OpenJDK Runtime Environment (build 11.0.17+8-post-Ubuntu-1ubuntu220.04)
OpenJDK 64-Bit Server VM (build 11.0.17+8-post-Ubuntu-1ubuntu220.04, mixed mode, sharing)

$ javac -version
javac 11.0.17

$ gcc --version
gcc (Ubuntu 9.4.0-1ubuntu1~20.04.1) 9.4.0
Copyright (C) 2019 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
```

### IntelliJ Setup

1. Install IntelliJ. I’m using IntelliJ IDEA Ultimate, which is the paid edition. But as a student, you can get this for
   free. If you’re having trouble with Ultimate edition, then there is also a free “Community Edition” as well.
2. Once in the IDE with your project, go to “File->Project Structure”
3. Under “Project Structure” tab on left hand size, click on “Project”. Then next to “SDK”, we will need to install a
   Java SDK to work. Pick any, but for reference I did it on OpenJDK 19.
4. Point Compiler output to compilerClassRootDir
5. Under libraries, click “+” to add libraries. And select “Java”. Add the directory “compileSrcRootDir” and
   “AST_lib/src”. When prompted, you will be asked “Library ___ will be added to the selected module” Select the project
   1 directory, not AST_lib. Under Modules, you may need to delete `AST_lib` if it was automatically added and you were
   seeing AST_lib as an option.
6. Go to compilerSrcRootDir/package1/OptimizingCompiler and a green play button should appear to run the code. Click on
   it. It will fail on first try, but that’s okay. We will fix that in the next step.
7. Go to “Run->Edit Configurations”. We need to tune this configuration to replicate the command line arguments that the
   Makefile provides. You will obviously need to add arguments such
   as: `inputProgramDir/trivia outputProgramDir/trivia.new.c cfgOutputDir/trivia.cfg`. In addition to that, click on "
   Modify Options", and under the Java options in the drop down menu, click on "Add VM options" and add `-ea` to the
   argument field.

If there is any error or issue with the setup, please contact [bcpark@ncsu.edu](mailto:bcpark@ncsu.edu).
