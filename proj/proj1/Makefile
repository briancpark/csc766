COMP_SRC_DIR = compilerSrcRootDir/
COMP_CLASS_DIR = compilerClassRootDir/
COMP_FILES =  package1/OptimizingCompiler package1/AstBuilder package1/TrivialSetup package1/CFG package1/BasicBlock package1/AssemblyBuilder
MAIN_CLASS = package1.OptimizingCompiler
#MAIN_CLASS = package1.ConstFolding

AST_LIB_PATH = ./AST_lib/src/
CLASSPATH = ${AST_LIB_PATH}:${COMP_CLASS_DIR}
JAVAC = javac
JAVA = java
JAVAC_FLAGS = -d ${COMP_CLASS_DIR} -classpath ${CLASSPATH}
JAVA_FLAGS = -ea -classpath ${CLASSPATH}


CC = gcc

INPUT_DIR = inputProgramDir/
OUTPUT_DIR = outputProgramDir/
CFG_OUTPUT_DIR = cfgOutputDir/

INPUT_PROG = automaton binrep fibonacci jacobi mandel movingedge multiply sort sort_insertion tax trivia
#INPUT_PROG = array
#INPUT_PROG = function
#INPUT_PROG = expression

#INPUT_PRGO = binrep
#INPUT_PROG = mandel
#INPUT_PROG = fibonacci
#INPUT_PROG = automaton
#INPUT_PROG = trivia
#INPUT_PRGO = tax
#INPUT_PROG = sort_insertion
#INPUT_PROG = sort
#IPNUT_PRGO = multiply
#INPUT_PROG = movingedge
#INPUT_PROG = jacobi


# These are LOG files that are created in the directories 
PROG_INPUT = programInput/

# file that contains the actual output of the executed programs, created on make run
PROG_OUTPUT = ${OUTPUT_DIR}programOutput

# a file that contains the output of make run, these are the instruction counts
INST_COUNT = ${OUTPUT_DIR}programInstCount

#CFG_OUTPUT = ${CFG_OUTPUT_DIR}

RM = rm
RM_FLAGS = -f -R


.SECONDARY:

# explicitly designate the targets phony, the command of these targets will not be creating a target file
# and if a file with the name of a target appears it will still be considered phony and the command will
# execute, otherwise the command would not execute because a current file exists.
.PHONY: all run compile build show clean

# run:		runs the output programs
# compile:	runs your compiler on the input programs and then compiles them with gcc
# build		builds class files your compiler from java source files
# show		shows the instruction count
# clean		deletes all class files and output files

all: run


#fibonacci 

run: ${INPUT_PROG:%=${OUTPUT_DIR}%.run}

	@${RM} ${RM_FLAGS} deleteOutputFiles
	@cat ${PROG_OUTPUT} | grep -i "instruction" > ${INST_COUNT}
	@cat ${INST_COUNT}

${OUTPUT_DIR}%.run: deleteOutputFiles ${OUTPUT_DIR}%
	@echo Executing $*
	@echo "" >> ${PROG_OUTPUT}
	@echo "" >> ${PROG_OUTPUT}
	@echo "Instruction Count for $*" >> ${PROG_OUTPUT}
	@${OUTPUT_DIR}$* < ${PROG_INPUT}$* >> ${PROG_OUTPUT}

# creates the deleteOutputFiles file in the project directory
# the operator >> appends to the file
deleteOutputFiles:
	@echo "" > ${PROG_OUTPUT}
	@echo "" > ${INST_COUNT}
	@${RM} ${PROG_OUTPUT} >> deleteOutputFiles
	@${RM} ${INST_COUNT} >> deleteOutputFiles

compile: ${INPUT_PROG:%=${OUTPUT_DIR}%}

${OUTPUT_DIR}%: ${OUTPUT_DIR}%.new.c
	@echo Compiling $*.new.c with ${CC}
	@${CC} ${OUTPUT_DIR}$*.new.c -o ${OUTPUT_DIR}$*

${OUTPUT_DIR}%.new.c: ${INPUT_DIR}%.adap ${INPUT_DIR}%.adap.h ${COMP_FILES:%=${COMP_CLASS_DIR}%.class}
	@echo Running the compiler on $* to make $*.new.c
#	@${JAVA} ${JAVA_FLAGS} ${MAIN_CLASS} ${INPUT_DIR}$*	
#	@${JAVA} ${JAVA_FLAGS} ${MAIN_CLASS} ${INPUT_DIR}$* ${OUTPUT_DIR}$*.new.c
	${JAVA} ${JAVA_FLAGS} ${MAIN_CLASS} ${INPUT_DIR}$* ${OUTPUT_DIR}$*.new.c ${CFG_OUTPUT_DIR}$*.cfg
#java package1.File1 inputProgramDir/... outputProgramDir/... .new.c



# build goes first
# COMP_FILES are the class names, the compiler files
build: ${COMP_FILES:%=${COMP_CLASS_DIR}%.class}
	@echo build target is going

#this $ seems to go before the build

${COMP_CLASS_DIR}%.class: ${COMP_FILES:%=${COMP_SRC_DIR}%.java}
	@echo dollar target under build is going
	@echo Compiling the compiler
	${JAVAC} ${JAVAC_FLAGS} ${COMP_FILES:%=${COMP_SRC_DIR}%.java}

# displays the instruction count file from outputProgramDir/
show:
	@cat ${INST_COUNT}

clean:
	${RM} ${RM_FLAGS} ${OUTPUT_DIR}*
	${RM} ${RM_FLAGS} ${COMP_CLASS_DIR}*
	${RM} ${RM_FLAGS} ${CFG_OUTPUT_DIR}*

