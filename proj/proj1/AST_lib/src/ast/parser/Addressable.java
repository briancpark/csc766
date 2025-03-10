/*
 * Addressable.java
 *
 * Created on September 24, 2001, 7:45 PM
 */

package ast.parser;

/**
 * @author Chen Ding
 * @description Accessible through a memory address.  Constants are caculable, but
 * not addressable.
 */
public interface Addressable extends Calculable {
    boolean equals(Addressable a2) throws AssertionError;
}

