package ast.scanner;

public final class Lexi {

    final static byte[] spaceChars = {' ', '\t', '\n', 13};

    final static byte[] symChars = {',', ';', '(', ')', '{', '}', '+', '-',
            '*', '/', '=', '>', '!', '-', '.', '<', ':', '!', '&', '[',
            ']', '%', '|', '&', '~'};

    final static byte[] idChars = {' '}; // pretend to be a list
    final static byte[] numChars = {' '};
    final static byte[] idnumChars = {' '};
    final static String reservedWords[] = {};
    final static String legalSymbols[] = {"(", ")", "{", "}", ",", ";",
            ".", "->", "*", "/", "==", "!=", "&&", "&", "||",
            "|", ">=", "=", ">>", "<<", ">", "<=", "<", "::", ":", "!",
            "[", "]", "+", "-", "%", "~"};

    public Lexi() {
    }

    public static boolean isIdChar(int c) {
        return searchFound(c, idChars);
    }

    public static boolean isNumChar(int c) {
        return searchFound(c, numChars);
    }

    public static boolean isSymChar(int c) {
        return searchFound(c, symChars);
    }

    static boolean searchFound(int c, byte list[]) {
        if (list == idChars)
            return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_')
                    || (c == '$'));

        if (list == numChars)
            return (c >= '0' && c <= '9');

        if (list == idnumChars)
            return (searchFound(c, idChars) || searchFound(c, numChars)
                    || c == '.');  // The last case is for floating-point constants

        for (int i = 0; i < list.length; i++)
            if (c == list[i]) return true;

        return false;
    }

    static boolean searchFound(String word, String list[]) {
        for (int i = 0; i < list.length; i++)
            if (list[i].compareTo(word) == 0) return true;

        return false;
    }
}
