/*
 * Comment.java
 *
 * Created on October 3, 2001, 11:25 PM
 */

package ast.parser;

import ast.scanner.IDToken;
import ast.scanner.OldScanner;
import ast.scanner.StringToken;
import tools.StringTools;

import java.io.IOException;

/**
 * @author administrator
 */
public class Comment extends AstNode implements Declarative {
    String comment;

    Comment(String vnm) {
        super(vnm);
    }

    public Comment(String vnm, String dt)
            throws AssertionError {
        super(vnm);
        comment = dt;
    }

    public String GetComment() throws AssertionError {
        assert comment != null;
        return comment;
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

    public String DumpAdap() throws AssertionError {
        comment = StringTools.AddQuotationMarks(comment);
        return " ( " + CommentLabel + " " + nodeName + " " + comment + ")\n";
    }

    public String DumpC() throws AssertionError {
        return " /* " + nodeName + " " + comment + "*/\n";
    }

}