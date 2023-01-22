package ast.parser;

import ast.scanner.*;
import tools.*;
import java.lang.*;
import java.util.*;
import java.io.*;

public class FileAst extends SegAst {
    public FileAst(String fnm) {super(fnm); }    
    
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
         // Wrong interface.               
         throw new AssertionError();
    }
    
    void ReadProgram(String fullFileName) throws IOException, AssertionError {
                        
        // Set the working directory to be this directory
        String wdir = StringTools.GetDirectoryName(fullFileName);
        if (wdir==null) wdir = System.getProperty("user.dir")+"/";
        // assert (wdir!=null);
        InsertAttribute("WorkingDirectory", new Comment(wdir));
        
        // Start reading file
        OldScanner scanner = new OldScanner(fullFileName);
        try {
            // Initialize file
            assert (scanner.MatchSym("("));
            assert (scanner.MatchID("FileAst"));
            nodeName = ((StringToken) scanner.GetNextToken()).GetString();
            assert (scanner.MatchID("Begin"));
            assert (scanner.MatchSym(")"));        
        
            // Read statements from file
            AstNode nn;
            for (nn = ReadNextAstNode(scanner); 
                 !(nn instanceof FileAst);
                 nn = ReadNextAstNode(scanner)) {
              assert (!(nn instanceof ProgAst));
              if (nn instanceof ImportFile) 
                ((ImportFile) nn).ReadProgram(scanner, wdir);
              else 
                nn.ReadProgram(scanner);
              AddChild(nn);        
            }

            // End of file (nn instanceof FileAst)
            assert (scanner.MatchID("End"));
            // delete nn, nt;
            assert (scanner.MatchSym(")"));
        }
        catch (Exception e) {
            System.err.println("Exception caught: " + e.getMessage());
            System.err.println("Program Format Error in file "+
                        fullFileName + " at line " + scanner.GetCurrentLineNo()+".");
            e.printStackTrace(System.err);
        }
    }
    
  public void Dump() throws IOException, AssertionError {
    String fname = GetWorkingDirectory()+
        StringTools.StripFileExtension(GetNodeName())+".out.adap";
    File fo = new File(fname);
    FileOutputStream fom = new FileOutputStream(fo);
    PrintStream fout = new PrintStream(fom);
    super.Dump(fout);
  }

  public void GenCode() throws IOException, AssertionError {
    String fname = GetWorkingDirectory()+
        StringTools.StripFileExtension(GetNodeName())+".out.c";
    File fo = new File(fname);
    FileOutputStream fom = new FileOutputStream(fo);
    PrintStream fout = new PrintStream(fom);
    GenCode(fout);
  }
    
  public void GenCode(PrintStream fout) throws IOException, AssertionError {
    /* Array types have special treatment.  We start with no array
       declaration in each file */
    ArrayType.declaredNames = new Hashtable();
    FuncType.declaredNames = new Hashtable();
    StructType.declaredNames = new Hashtable();
    
    super.GenCode(fout);
  }
}


    
