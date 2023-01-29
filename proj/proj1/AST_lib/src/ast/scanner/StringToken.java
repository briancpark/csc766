package ast.scanner;
import java.io.*;

public class StringToken extends Token {
  private String str;
  public StringToken(String _string) {
    str = _string;
  }

  public String GetString() {return str;}

  public void PrintToken(PrintStream outputFile) throws IOException {
    outputFile.print(str);
  }
}
