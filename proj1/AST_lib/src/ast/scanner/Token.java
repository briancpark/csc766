package ast.scanner; 

import java.io.*;

public abstract class Token {
   abstract public void PrintToken(PrintStream outputFile) throws IOException;
}
