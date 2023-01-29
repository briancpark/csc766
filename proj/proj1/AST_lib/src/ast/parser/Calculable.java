/*
 * Calculable.java
 *
 * Created on September 24, 2001, 4:26 PM
 */

package ast.parser;

/**
 * @author administrator
 * @version
 * @description An interface to everything that can appear on the right-hand
 * side of an assignment.  Guaranteed to be an AstNode, so one can access
 * AstNode functions by converting it to an AstNode.
 */

import java.io.IOException;
import java.io.PrintStream;

public interface Calculable {
    public void Dump(PrintStream fout) throws IOException, AssertionError;

    public void GenCode(PrintStream fout) throws IOException, AssertionError;

    public String DumpAdap() throws AssertionError;

    public String DumpC() throws AssertionError;
}
