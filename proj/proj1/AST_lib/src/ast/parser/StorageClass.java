/*
 * StorageClass.java
 *
 * Created on February 26, 2002, 10:09 PM
 */

package ast.parser;
import tools.*;

/** Storage class of data or function objects.  It can be AUTO, EXTERN, STATIC
 *  or REGISTER.
 * @author  administrator
 * @version 
 */
public class StorageClass extends Object {

    private String name;
    public String GetName() {return name;}
    
    /** Creates new StorageClass */
    private StorageClass(String nameStr) {
        name = nameStr;
    }
    
    public static StorageClass GetStorageClass(String sclass) throws AssertionError {
        if (sclass.compareTo("AUTO")==0) return AUTO;
        if (sclass.compareTo("EXTERN")==0) return EXTERN;
        if (sclass.compareTo("STATIC")==0) return STATIC;
        if (sclass.compareTo("REGISTER")==0) return REGISTER;
        else throw new AssertionError();
    }
    
    public static final StorageClass AUTO = new StorageClass("");
    public static final StorageClass EXTERN = new StorageClass("extern");
    public static final StorageClass STATIC = new StorageClass("static");
    public static final StorageClass REGISTER = new StorageClass("register");
}