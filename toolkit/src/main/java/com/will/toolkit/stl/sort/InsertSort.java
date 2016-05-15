/*
 * $Header: InsertSort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 上午11:57:24
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

import java.util.Comparator;

/**
 * InsertSort 插入排序
 * @author will
 * @version 1.0.0.0 2012-5-11 上午11:57:24
 */
public class InsertSort<E extends Comparable<E>> extends Sort<E> {

    /**
     * 排序
     * @param array 要排序的数组
     * @param fromIndex 排序开始的位置
     * @param endIndex 排序结束的位置
     * @param c 比较器
     * @version 1.0.0.0 2012-5-11 上午11:48:16
     */
    @Override
    public void sort(E[] array, int fromIndex, int endIndex, Comparator<E> c) {
        for (int i = fromIndex; i < endIndex; i++) {
            for (int j = fromIndex; j <= i; j++) {
                E insertElement = array[i];
                if (c.compare(array[j], insertElement) > 0) {
                    move(array, j, i);
                    array[j] = insertElement;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Integer[] array = new Integer[] { 9,5,1,4,1,2,65 };
        InsertSort<Integer> sort = new InsertSort<Integer>();
        sort.sort(array);
        sort.print(array);
        
    }

}
