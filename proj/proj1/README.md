# Project 1 Phase 2: Data Flow

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

I implemented Avail Set Analysis, and you can see the avail expressions printed out for each basic block when running
the commands.

```sh

Note that on VCL, you may need to install `javac`:

```sh
sudo apt install default-jdk -y
``` 

## File and Directory Descriptions

The starter code in this repository will look different as it has been linted and reformatted. To show the difference,
here is the `git diff`:

```diff
briancpark@Brians-MacBook-Pro proj1 % git diff --stat acb76f1b213df764f5f9d5a2ead77a9cc4c7354c
 proj/proj1/README.md                                           |  74 +++++++++++++++++++-------------------
 proj/proj1/README1A.md                                         | 133 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 proj/proj1/compilerSrcRootDir/package1/BasicBlock.java         | 100 ++++++++++++++++++++++++++++++++++++++++++++++++---
 proj/proj1/compilerSrcRootDir/package1/OptimizingCompiler.java |  96 ++++++++++++++++++++++++++++++++++++++++++++++++-
 proj/proj1/proj1B.tar                                          | Bin 0 -> 1536308 bytes
 5 files changed, 360 insertions(+), 43 deletions(-)
```

## Design Document

The codebase was modified to do Data Flow Analysis of Available Expression sets. To accomplish this, I added a
subroutine `availDFA(prog)` to the `OptimizingCompiler` class. This subroutine first computes the DEExpr and ExprKillÒ
sets for each basic block in the control flow graph given. After that, I apply the avail set analysis algorithm
mentioned in class into practice using Java sets. The implementation is pretty straightforward based on the pseudocode
presented from lecture.

## Available Expression Sets

There were no optimizations done with available expression sets. Since this is Data Flow Analysis, the only thing done
was printing out the available sets for each basic blocks. The output shows that there are sometimes no sets for certain
programs, and that is because some of those programs are filled with only control statements like if and else, filled
with constants, or filled with array assignments. These are all cases where these functionalities weren't implemented,
mainly because we weren't taught how to deal with array assignments and the implementation could be a little tricky to
consider as array are mutable. Expressions were considered with variables. I also considered expressions with constants.

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