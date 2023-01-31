# Project 1 Phase I: Value Numbering

Brian Park

Here lies the README.md for the project. Feel free to view the Markdown file in a text editor or browser to properly
render the formatting. Otherwise, viewing the raw text should suffice.


## Building and Running the Project
To compile and run the code, it should be the commands below. There was nothing changed compared to the original starter
code.

```sh
make compile
make build
make run
```

## File and Directory Descriptions
The starter code in this repository will look different as it has been linted and reformatted. To show the difference, here is the `git diff`:
```diff
briancpark@Brians-MBP proj1 % git diff --stat ce1412e294166dba5f6eb3
 proj/proj1/README.md                                           |  45 +++++++++++++++++++------
 proj/proj1/compilerSrcRootDir/package1/BasicBlock.java         | 122 +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
 proj/proj1/compilerSrcRootDir/package1/OptimizingCompiler.java |  19 +++++++++++
 3 files changed, 175 insertions(+), 11 deletions(-)
```

## Design Document

The codebase was modified to add support for redundancy elimination via value numbering on the control flow graph. To 
accomplish this, I added a subroutine `valueNumbering(prog)` to the `OptimizingCompiler` class. This subroutine first
computes the two hash tables required for value numbering, `valueNumberTable` and `rewrittenTable`. This was only down
at a basic block level, so I added two more functions, `valueNumbering` and `rewrite` to the `BasicBlock` class.

## Optimizations: Value Numbering

## Debugging and Other Notes
This is some other notes included for my own sanity and development.

### Java Version
Below are the versions used on macOS (Apple Silicon):
```
java version "19" 2022-09-20
javac 19
```

On VCL, the versions are as follows:
```
```

### IntelliJ Setup



If there is any error or issue with the setup, please contact [bcpark@ncsu.edu](mailto:bcpark@ncsu.edu).