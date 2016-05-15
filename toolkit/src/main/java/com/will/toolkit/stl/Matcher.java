/*
 * $Header: Matcher.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2014-11-30 下午02:29:34
 * $Owner: will
 */
package com.will.toolkit.stl;

/**
 * Matcher 查找器
 * @author will
 * @version 1.0.0.0 2014-11-30 下午02:29:34
 */
public interface Matcher<T> {
    /**
     * 是否匹配
     * @param value
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:30:22
     */
    boolean match(T value);
}
