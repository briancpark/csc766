package ast.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

public final class OldScanner {
    private FileInputStream fin;
    private int nextChar;
    private Token curToken;
    private String fname;
    private int lineno;

    public OldScanner(String inFileName) throws AssertionError {
        fname = inFileName;
        lineno = 0;

        try {
            File inFile = new File(inFileName);
            fin = new FileInputStream(inFile);

            nextChar = fin.read();
            curToken = ScanNextToken();
        } catch (IOException e) {
            System.err.println("OldScanner: cannot open file " + inFileName + " for read.");
            throw new AssertionError();
        }
    }

    public static void main(String[] args) throws IOException, AssertionError {
        OldScanner scanner = new OldScanner(args[0]);
        Vector tokens = new Vector();
        while (scanner.HasMoreToken()) {
            Token t = scanner.GetNextToken();
            tokens.addElement(t);
        }
        for (int i = 0; i < tokens.size(); i++) {
            ((Token) tokens.get(i)).PrintToken(System.out);
            System.out.print(" ");
        }
    }

    public String GetFileName() {
        return fname;
    }

    public int GetCurrentLineNo() {
        return lineno;
    }

    public boolean HasMoreToken() {
        return curToken != null;
    }

    public boolean MatchID(String tnm) throws IOException, AssertionError {
        if (!HasMoreToken()) return false;
        if (!(curToken instanceof IDToken)) return false;
        if (tnm.compareTo(((StringToken) curToken).GetString()) != 0) return false;
        curToken = ScanNextToken();
        return true;
    }

    public boolean MatchSym(String tnm) throws IOException, AssertionError {
        if (!HasMoreToken()) return false;
        if (!(curToken instanceof SymToken)) return false;
        if (tnm.compareTo(((StringToken) curToken).GetString()) != 0) {
            System.err.println("\nAdap Error. " + GetFileName() + ":" + GetCurrentLineNo() + " '" + tnm +
                    "' expected but seeing '" + ((StringToken) curToken).GetString() + "'");
            return false;
        }
        curToken = ScanNextToken();
        return true;
    }

    public Token GetNextToken() throws AssertionError {
        try {
            assert (curToken != null);
            Token tmp = curToken;
            curToken = ScanNextToken();
            return tmp;
        } catch (IOException e) {
            System.err.println("OldScanner.GetNextToken: unknown token encountered at line "
                    + lineno + ".");
            return null;
        }
    }

    public Token PreviewNextToken() {
        return curToken;
    }

    SymToken RecognizeSymToken() throws IOException, AssertionError {
        byte b1 = (byte) nextChar;
        nextChar = fin.read();
        SymToken newsym;
        for (int i = 0; i < Lexi.legalSymbols.length; i++) {
            byte sym[] = Lexi.legalSymbols[i].getBytes();
            if (b1 != sym[0]) continue;
            if (sym.length == 1)
                return new SymToken(Lexi.legalSymbols[i]);
            else if (nextChar != -1 && nextChar == sym[1]) {
                nextChar = fin.read();
                return new SymToken(Lexi.legalSymbols[i]);
            }
        }
        System.out.println("OldScanner: cannot recognize symbol " + (char) b1 + (char) nextChar);
        assert (false);
        return null;
    }

    Token ScanNextToken() throws IOException, AssertionError {
        if (nextChar == -1)
            return null;

        if (nextChar == '#')
            return new Meta(ScanUntil('\n'));
        if (nextChar == '"')
            return new StringConst(ScanUntil('"'));

        if (Lexi.searchFound(nextChar, Lexi.symChars))
            return RecognizeSymToken();

        String nstr;

        if (Lexi.searchFound(nextChar, Lexi.spaceChars)) {
            nstr = ScanNextString(Lexi.spaceChars);
            return ScanNextToken();
        } else if (Lexi.searchFound(nextChar, Lexi.numChars)) {
            nstr = ScanNextString(Lexi.idnumChars);
            return new NumToken(nstr);
        }
        // searchFound( , idChars) must come at the last position
        else if (Lexi.searchFound(nextChar, Lexi.idChars)) {
            nstr = ScanNextString(Lexi.idnumChars);
            if (Lexi.searchFound(nstr, Lexi.reservedWords))
                return new RWordToken(nstr);
            else
                return new IDToken(nstr);
        } else {
            System.err.println("Unknown character " + (char) nextChar + " ASC code " + (int) nextChar);
            assert (false); // unknown alphabet
            return null;
        }
    }

    // Keep searching for the same type of chars and
// put them in scratch.
    String ScanNextString(byte[] list) throws IOException, AssertionError {
        StringBuffer str = new StringBuffer();
        while (Lexi.searchFound(nextChar, list)) {
            if (nextChar == '\n') lineno++;
            str.append((char) nextChar);
            nextChar = fin.read();
        }

        return str.toString();
    }

    // Test for Meta statements and string constants
    String ScanUntil(char endChar) throws IOException, AssertionError {
        StringBuffer str = new StringBuffer();
        str.append((char) nextChar);
        int fstChar = nextChar;
        nextChar = fin.read();
        // Special tests for Meta statements: #, //, or /* ... */
        if (fstChar == '/') {
            if (nextChar == '/') endChar = '\n';
            else if (nextChar == '*') endChar = '/';
        }
        while (nextChar != endChar && nextChar != -1) {
            str.append((char) nextChar);
            nextChar = fin.read();
        }

        // conservative test
        if (endChar == '/') assert (str.charAt(str.length() - 1) == '*');

        assert (nextChar != -1);
        str.append((char) nextChar);
        nextChar = fin.read();

        return str.toString();
    }
} // End of OldScanner class
