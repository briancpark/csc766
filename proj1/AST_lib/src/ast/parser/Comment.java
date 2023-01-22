/*
 * Comment.java
 *
 * Created on October 3, 2001, 11:25 PM
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
 */
public class Comment extends AstNode implements Declarative {
    String comment;
    public String GetComment() throws AssertionError {
        assert comment!=null; 
        return comment;
    }
    
    Comment(String vnm) {super(vnm);}
    public Comment(String vnm, String dt) 
            throws AssertionError {
        super(vnm); comment = dt; 
    }
        
    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        StringToken ct = (StringToken) scanner.GetNextToken();
        comment = ct.GetString();
        while (ct instanceof IDToken) {
            comment = comment + " " + ct.GetString();
            scanner.GetNextToken();
            ct = (StringToken) scanner.PreviewNextToken();
        }
        assert (scanner.MatchSym(")"));
    }
    
    public String DumpAdap() throws AssertionError  {
        comment = StringTools.AddQuotationMarks(comment);
        return " ( " + CommentLabel + " " + nodeName + " " + comment+")\n";
    }
    
    public String DumpC() throws AssertionError {
        return " /* " + nodeName + " " + comment +"*/\n";
    }
        
}