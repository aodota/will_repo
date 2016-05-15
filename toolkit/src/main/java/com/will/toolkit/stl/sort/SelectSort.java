/*
 * $Header: SelectSort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 上午11:50:15
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

import java.util.Comparator;

/**
 * SelectSort 选择排序 
 * @author will
 * @version 1.0.0.0 2012-5-11 上午11:50:15
 */
public class SelectSort<E extends Comparable<E>> extends Sort<E> {

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
            int minIndex = i;
            for (int j = i + 1; j < endIndex; j++) {
                if (c.compare(array[minIndex], array[j]) > 0) {
                    minIndex = j;
                }
            }
            
            swap(array, minIndex, i);
        }
    }

}
