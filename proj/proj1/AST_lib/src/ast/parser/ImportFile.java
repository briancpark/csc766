/*
 * ImportFile.java
 *
 * Created on October 10, 2001, 1:32 PM
 */

package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author  administrator
 * @version 
 * (ImportFile file_name)
 * import a file (FileAst), merge in all the type and symbol definitions as well
 * as the code.  As if the file is embedded at the location of the ImportFile.
 */
public class ImportFile extends AstNode {
    // FileAst importedFile;
    FileAst GetImportedFile() throws AssertionError {
        return (FileAst) GetChild(0);
    }

    /** Creates new ImportFile */
    public ImportFile(String tnm) {super(tnm);}
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
         // Wrong interface.               
         throw new AssertionError();
    }

    void ReadProgram(OldScanner scanner, String workDir) 
                throws IOException, AssertionError {
        assert (scanner.MatchSym(")"));
        String fname = StringTools.StripQuotationMarks(GetNodeName());
        if (StringTools.GetDirectoryName(fname)==null) {
            // Add directory prefix
            assert (workDir!=null);
            fname =  workDir + fname;
        }
        FileAst f = new FileAst(fname);
        f.ReadProgram(fname);
        AddChild(f);
    }
}