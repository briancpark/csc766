/*
 * AttributedNode.java
 *
 * Created on November 5, 2001, 8:55 AM
 */

package tools;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * A node that supports insertion, query and deletion of attributes in
 * the form of <String, Object> pairs.
 *
 * @author administrator
 */
public class AttributedNode extends Object implements Cloneable {
    Hashtable attrTable;

    /**
     * Creates new AttributedNode
     */
    public AttributedNode() {
        attrTable = new Hashtable();
    }

    public void InsertAttribute(String nm, Object attr) throws AssertionError {
        assert !attrTable.contains(nm);
        attrTable.put(nm, attr);
    }

    public Object GetAttribute(String nm) {
        return attrTable.get(nm);
    }

    public void DeleteAttribute(String nm) throws AssertionError {
        assert attrTable.containsKey(nm);
        attrTable.remove(nm);
    }

    public Enumeration GetAttributes() {
        return attrTable.elements();
    }

    public Object freshclone() throws CloneNotSupportedException {
        AttributedNode nt = (AttributedNode) super.clone();
        nt.attrTable = new Hashtable();
        return nt;
    }

    public Object clone() throws CloneNotSupportedException {
        AttributedNode nt = (AttributedNode) super.clone();
        nt.attrTable = (Hashtable) attrTable.clone();
        return nt;
    }
}
