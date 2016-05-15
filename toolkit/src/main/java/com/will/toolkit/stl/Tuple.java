/*
 * $Header: Tuple.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-10-16 下午05:23:31
 * $Owner: will
 */
package com.will.toolkit.stl;

import java.io.Serializable;

/**
 * Tuple
 * @author will
 * @version 1.0.0.0 2011-10-16 下午05:23:31
 *
 */
public class Tuple<L, R> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -547485016218086570L;

    /** left object */
    public L left;
    
    /** right object */
    public R right;
    
    /**
     * 默认构造函数
     */
    public Tuple() {
        
    }
    
    /**
     * 数据结构
     * @param left 左边对象
     * @param right 右边对象
     */
    public Tuple(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    /**
     * 获得HashCode()
     * @see Object#hashCode()
     */
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }
    
    @SuppressWarnings("rawtypes")
    public boolean equals(Object other) {
        if (other instanceof Tuple) {
            Tuple to = (Tuple) other;
            return (left.equals(to.left) && right.equals(to.right));
        } else {
            return false;
        }
    }
    
    public String toString() {
        return "[left=" + left + ", right=" + right + "]";
    }
}
