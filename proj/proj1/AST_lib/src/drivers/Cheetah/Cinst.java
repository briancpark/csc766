/*
 * ClassName.java
 *
 * Created on February 13, 2002, 5:12 PM
 */

package drivers.Cheetah;

import ast.parser.*;
import ast.simplifier.*;
import reda.Cheetah.DataRecorder;
import tools.*;
import java.lang.*;
import java.io.*;

/**
 *
 * @author  administrator
 * @version 
 */
public class Cinst extends Object {

    /** Creates new ClassName */
    public Cinst() {
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) throws AssertionError, IOException {
        for (int i=0; i<args.length; i++) {
            System.err.print("Parsing the intermediate form of "+args[i]+"...");
            ProgAst prog = new ProgAst(args[i]);
            LocalSimplifier.Simplify(prog);
            System.err.print("Done.\nGenerating C code for "+args[i]+"...");
            prog.GenCode();
            prog.Dump();
            System.err.print("Done.\nInstrumenting "+args[i]+"...");
            DataRecorder dr = new DataRecorder();
            dr.InstrumentProgram(prog);
            System.err.println("Done.");
        }
    }
}