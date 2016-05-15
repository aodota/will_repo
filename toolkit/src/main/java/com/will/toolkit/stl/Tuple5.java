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
public class Tuple5<F, S, T, FR, FI> {
    /** first object */
    public F first;
    
    /** second object */
    public S second;
    
    /** three object*/
    public T three;
    
    /** four object */
    public FR four;
    
    /** five object */
    public FI five;
    
    /**
     * 默认构造函数
     */
    public Tuple5() {
        
    }
    
    /**
     * Tuple3
     * @param first
     * @param second
     * @param three
     */
    public Tuple5(F first, S second, T three, FR four, FI five) {
        super();
        this.first = first;
        this.second = second;
        this.three = three;
        this.four = four;
        this.five = five;
    }



    /**
     * 获得HashCode()
     * @see Object#hashCode()
     */
    public int hashCode() {
        return first.hashCode() ^ second.hashCode() ^ three.hashCode() ^ four.hashCode() ^ five.hashCode();
    }
    
    @SuppressWarnings("rawtypes")
    public boolean equals(Object other) {
        if (other instanceof Tuple5) {
            Tuple5 to = (Tuple5) other;
            return (first.equals(to.first) && second.equals(to.second)) && three.equals(to.three) && four.equals(to.four) && five.equals(to.five);
        } else {
            return false;
        }
    }
    
    public String toString() {
        return "[first=" + first + ", second=" + second + ", three= " + three + ", four=" + four + ", five=" + five + "]";
    }
}
