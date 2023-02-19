/*
 * ClassName.java
 *
 * Created on February 13, 2002, 5:12 PM
 */

package drivers;

import ast.parser.ProgAst;
import ast.simplifier.LocalSimplifier;
import reda.*;

import java.io.IOException;

/**
 * @author administrator
 */
public class Cinst extends Object {

    /**
     * Creates new ClassName
     */
    public Cinst() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws AssertionError, IOException {
        boolean inst = false;
        boolean all = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i].equals("-inst")) {
                    System.err.println("Instrumenting with RecInst insertions");
                    inst = true;
                } else if (args[i].equals("-all")) {
                    System.err.println("Recording ALL memory accesses");
                    all = true;
                } else if (!args[i].equals("-acc")) {
                    System.err.println("Valid flags are:");
                    System.err.println("\t-acc\tInstrument with RecAccess insertions. (default)");
                    System.err.println("\t-all\tRecord ALL memory accesses");
                    System.err.println("\t-inst\tInstrument with RecInst insertions.");
                    System.exit(0);
                }
            }
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) != '-') {
                System.err.print("Parsing the intermediate form of " + args[i] + "...");
                ProgAst prog = new ProgAst(args[i]);
                LocalSimplifier.Simplify(prog);
                System.err.print("Done.\nGenerating C code for " + args[i] + "...");
                prog.GenCode();
                prog.Dump();
                System.err.print("Done.\nInstrumenting " + args[i] + "...");
                //DataRecorder dr = new DataRecorder();//all);
                //dr.InstrumentProgram(prog);

                GeneralRecorder dr;
                if (inst)
                    dr = new InstRecorder(prog);
                else {
                    if (all)
                        dr = new AccessRecorder(prog, true);
                    else
                        dr = new AccessRecorder(prog);
                }
                dr.InstrumentProgram();
                dr.GenerateCode();

                System.err.println("Done.");
            }
        }
    }
}
