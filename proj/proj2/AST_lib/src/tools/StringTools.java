package tools;

public class StringTools {
    public static String TruncatedClassName(String nm) {
        int dotpos = nm.lastIndexOf('.');
        return nm.substring(dotpos + 1, nm.length());
    }

    public static String StripQuotationMarks(String nm) {
        if (nm.charAt(0) != '"') return nm;
        else return nm.substring(1, nm.length() - 1);
    }

    public static String AddQuotationMarks(String nm) {
        if (nm.charAt(0) == '"') return nm;
        else return "\"" + nm + "\"";
    }

    public static String StripFileExtension(String nm) {
        int endIdx;
        nm = StripQuotationMarks(nm);
        endIdx = nm.indexOf(".");
        if (endIdx == -1) endIdx = nm.length();
        return nm.substring(0, endIdx);
    }

    // Return the prefix before the last '/'
    public static String GetDirectoryName(String fname) {
        if (fname.lastIndexOf('/') == -1) return null;
        return fname.substring(0, fname.lastIndexOf('/') + 1);
    }

    // Convert a string to a valid id, e.g. int i[5] --> int_i_5_
    public static String ConvertToIdentifier(String nm) {
        for (int i = 0; i < nm.length(); i++)
            if (!(ast.scanner.Lexi.isIdChar(nm.charAt(i))
                    || (ast.scanner.Lexi.isNumChar(nm.charAt(i)))))
                nm = nm.replace(nm.charAt(i), '_');
        return nm;
    }
}