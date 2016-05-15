/*
 * $Header: DoubleIterator.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-26 下午2:24:51
 * $Owner: will
 */
package com.will.toolkit.stl;

/**
 * DoubleIterator
 * @author will
 * @version 1.0.0.0 2012-5-26 下午2:24:51
 */
public interface DoubleIterator <E> {
    public boolean hasNext();
    public boolean hasPrev();
    public E next();
    public E prev();
    public void remove();
}
