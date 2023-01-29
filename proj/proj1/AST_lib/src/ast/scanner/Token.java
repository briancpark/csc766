package ast.scanner;

import java.io.IOException;
import java.io.PrintStream;

public abstract class Token {
    abstract public void PrintToken(PrintStream outputFile) throws IOException;
}
