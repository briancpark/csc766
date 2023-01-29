package ast.parser;

import ast.scanner.OldScanner;
import ast.scanner.StringToken;
import ast.simplifier.LocalSimplifier;
import tools.StringTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ProgAst extends SegAst {
    public ProgAst(String pnm) throws AssertionError {
        super(pnm);
        PrimitiveType it = new PrimitiveType("int");
        typeTable.put("int", it);
        it = new PrimitiveType("char");
        typeTable.put("char", it);
        it = new PrimitiveType("short");
        typeTable.put("short", it);
        it = new PrimitiveType("double");
        typeTable.put("double", it);
        it = new PrimitiveType("float");
        typeTable.put("float", it);
        it = new PrimitiveType("long");
        typeTable.put("long", it);
        it = new PrimitiveType("unsigned");
        typeTable.put("unsigned", it);
        PrimitiveType voidtyp = new PrimitiveType("void");
        typeTable.put("void", voidtyp);
        it = new PrimitiveType("unsigned char");
        typeTable.put("unsigned char", it);
        it = new PrimitiveType("unsigned short");
        typeTable.put("unsigned short", it);
        it = new PrimitiveType("unsigned int");
        typeTable.put("unsigned int", it);
        it = new PrimitiveType("unsigned long");
        typeTable.put("unsigned long", it);
        it = new PrimitiveType("long int");
        typeTable.put("long int", it);
        it = new PrimitiveType("long long int");
        typeTable.put("long long int", it);

        // Dummy type so that function names can enter symbol table
        //it = new PrimitiveType("function");
        //typeTable.put("function", it);
        DataType ret = new DataType(voidtyp, 0);
        FuncType dummy = new FuncType(ret);
        typeTable.put("dummy", dummy);
        symbolTable.put("printf", new VarAst("printf",
                new DataType(dummy, 0)));

        // Read the program
        try {
            ReadProgram(pnm);
        } catch (Exception e) {
            System.err.println("Exception caught: " + e.getMessage());
            e.printStackTrace(System.err);
            System.err.println("Intermediate-Format Reading Error in program " +
                    pnm + ".");
        }
    }

    public static void main(String[] args) throws IOException, AssertionError {
        // ProgAst prog = new ProgAst("C:/Adap/tests/qsort/all.adap");
        // ProgAst prog = new ProgAst("C:/Adap/tests/func/func.adap");
        // ProgAst prog = new ProgAst("C:/Adap/tests/2Darray/array.adap");
        // ProgAst prog = new ProgAst("C:/DingFiles/Reda/Tests/C/ImageThining/Try/skeleton.adap");
        /*prog.Dump(System.out);
        prog.GenCode(System.out); */
        ProgAst prog = new ProgAst("C:/Adap/tests/misc/typeDef.adap");
        LocalSimplifier.Simplify(prog);
        prog.GenCode();
        prog.Dump();
    }

    void ReadProgram(OldScanner scanner) throws IOException, AssertionError {
        // Wrong interface
        throw new AssertionError();
    }

    void ReadProgram(String fullFileName) throws IOException, AssertionError {

        // Set the working directory to be this directory
        String wdir = StringTools.GetDirectoryName(fullFileName);
        if (wdir == null) wdir = System.getProperty("user.dir") + "/";
        //assert (wdir!=null);
        InsertAttribute("WorkingDirectory", new Comment(wdir));

        // Start reading file
        OldScanner scanner = new OldScanner(fullFileName);
        // Initialize file
        assert (scanner.MatchSym("("));
        StringToken ftype = (StringToken) scanner.GetNextToken();
        if (ftype.GetString().compareTo(ProgAstLabel) != 0) {
            // A single file program
            nodeName = ((StringToken) scanner.GetNextToken()).GetString();
            FileAst f = new FileAst(nodeName);
            // ImportFile will use the f as the parent for its children
            f.ReadProgram(fullFileName);
            AddChild(f);
        } else {
            // A program file with a list of ImportFile nodes
            nodeName = ((StringToken) scanner.GetNextToken()).GetString();
            assert (scanner.MatchID("Begin"));
            assert (scanner.MatchSym(")"));

            // Read statements from file
            AstNode nn;
            for (nn = ReadNextAstNode(scanner);
                 nn instanceof ImportFile;
                 nn = ReadNextAstNode(scanner)) {
                ((ImportFile) nn).ReadProgram(scanner, wdir);
                assert (((ImportFile) nn).GetNumChildren() == 1
                        && ((ImportFile) nn).GetChild(0) instanceof FileAst);
                nn.GetChild(0).MoveMeAsChildOf(this);
            }

            // End of program file (nn instanceof PorgAst)
            assert (nn == null);
            assert (scanner.MatchID("End"));
            // delete nn, nt;
            assert (scanner.MatchSym(")"));

            LocalSimplifier.Simplify(this);
        }

        try {
            CheckConsistency();
        } catch (Exception e) {
            System.err.println(e.getMessage() + ". Consistency check failed.");
            e.printStackTrace(System.err);
        }
    }

    public void Dump() throws IOException, AssertionError {
        PrintStream fout = new PrintStream(new FileOutputStream
                (new File(GetWorkingDirectory() + "prog.adap")));
        Dump(fout);
    }

    public String DumpAdap() throws AssertionError {
        String str = "( " + ProgAstLabel + " " + nodeName + " Begin )";
        for (int i = 0; i < GetNumChildren(); i++) {
            FileAst f = (FileAst) GetChild(i);
            str += "( " + ImportFileLabel + " " + f.GetNodeName() + ")\n";
            str += f.DumpAdap();
        }
        str += "( " + ProgAstLabel + " " + nodeName + " End )\n";
        return str;
    }

    public void GenCode() throws IOException, AssertionError {
        FileAst f;
        for (int i = 0; i < GetNumChildren(); i++) {
            f = (FileAst) GetChild(i);
            f.GenCode();
        }
    }

}
