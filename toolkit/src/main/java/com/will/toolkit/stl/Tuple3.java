/*
 * $Header: Tuple3.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-4-21 下午2:56:31
 * $Owner: will
 */
package com.will.toolkit.stl;

/**
 *
 * @author will
 * @version 1.0.0.0 2012-4-21 下午2:56:31
 */
public class Tuple3<F, S, T> {
    /** first object */
    public F first;
    
    /** second object */
    public S second;
    
    /** three object*/
    public T three;
    
    /**
     * 默认构造函数
     */
    public Tuple3() {
        
    }
    
    /**
     * Tuple3
     * @param first
     * @param second
     * @param three
     */
    public Tuple3(F first, S second, T three) {
        super();
        this.first = first;
        this.second = second;
        this.three = three;
    }



    /**
     * 获得HashCode()
     * @see Object#hashCode()
     */
    public int hashCode() {
        return first.hashCode() ^ second.hashCode() ^ three.hashCode();
    }
    
    @SuppressWarnings("rawtypes")
    public boolean equals(Object other) {
        if (other instanceof Tuple3) {
            Tuple3 to = (Tuple3) other;
            return (first.equals(to.first) && second.equals(to.second)) && three.equals(to.three);
        } else {
            return false;
        }
    }
    
    public String toString() {
        return "[first=" + first + ", second=" + second + ", three= " + three + "]";
    }
}
