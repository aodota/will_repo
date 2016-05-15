/*
 * $Header: Sort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 上午11:43:18
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

import java.util.Comparator;

/**
 * Sort 排序
 * @author will
 * @version 1.0.0.0 2012-5-11 上午11:43:18
 */
public abstract class Sort<E extends Comparable<E>> {
    /** 默认排序器  */
    public final Comparator<E> DEFAULT_ORDER = new DefaultComparator();
    /** 反序排序器 */
    public final Comparator<E> REVERSE_ORDER = new ReverseComparator();
    
    /**
     * 排序
     * @param array 要排序的数组
     * @param fromIndex 排序开始的位置
     * @param endIndex 排序结束的位置
     * @param c 比较器
     * @version 1.0.0.0 2012-5-11 上午11:48:16
     */
    public abstract void sort(E[] array, int fromIndex, int endIndex, Comparator<E> c);
    
    /**
     * 排序
     * @param array
     * @version 1.0.0.0 2012-5-11 上午11:49:17
     */
    public void sort(E[] array) {
        sort(array, 0, array.length, DEFAULT_ORDER);
    }
    
    /**
     * 反序
     * @param array
     * @version 1.0.0.0 2012-5-11 上午11:49:51
     */
    public void sortReverse(E[] array) {
        sort(array, 0, array.length, REVERSE_ORDER);
    }
    
    /**
     * 交换
     * @param array
     * @param i
     * @param j
     * @version 1.0.0.0 2012-5-11 上午11:53:50
     */
    protected final void swap(E[] array, int i, int j) {
        if (i != j) {
            E temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }
    
    /**
     * 移动
     * @param array
     * @param fromIndex
     * @param toIndex
     * @version 1.0.0.0 2012-5-11 上午11:57:00
     */
    protected final void move(E[] array, int fromIndex, int toIndex) {
        move(array, fromIndex, toIndex, 1);
    }
    
    /**
     * 移动
     * @param array
     * @param fromIndex
     * @param toIndex
     * @param step
     * @version 1.0.0.0 2012-5-11 上午11:56:37
     */
    protected final void move(E[] array, int fromIndex, int toIndex, int step) {
        if (fromIndex != toIndex) {
            for (int i = toIndex; i > fromIndex; i = i - step) {
                array[i] = array[i - step];
            }
        }
    }
    
    /**
     * 打印
     * @param array
     * @version 1.0.0.0 2012-5-11 下午12:05:44
     */
    protected final void print(E[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    
    /** 默认排序器(一般为升序) */
    private class DefaultComparator implements Comparator<E> {
        /**
         * @see Comparator#compare(Object, Object)
         */
        @Override
        public int compare(E o1, E o2) {
            return o1.compareTo(o2);
        }
    }

    /** 反序排序器(一般为升序) */
    private class ReverseComparator implements Comparator<E> {
        /**
         * @see Comparator#compare(Object, Object)
         */
        @Override
        public int compare(E o1, E o2) {
            return o2.compareTo(o1);
        }
    }
}
