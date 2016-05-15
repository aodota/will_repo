/*
 * $Header: HeapSort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 下午12:35:41
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

import java.util.Comparator;

/**
 * @author will
 * @version 1.0.0.0 2012-5-11 下午12:35:41
 */
public class HeapSort<E extends Comparable<E>> extends Sort<E> {

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
        initialHeap(array, fromIndex, endIndex, c);
        for (int i = endIndex - fromIndex; i >= 1; i--) {
            swap(array, fromIndex, i);
            initialHeap(array, fromIndex, i, c);
        }
    }

    /**
     * 初始化堆
     * @param array
     * @param fromIndex
     * @param endIndex
     * @param c
     * @version 1.0.0.0 2012-5-11 下午12:36:34
     */
    private void initialHeap(E[] array, int fromIndex, int endIndex, Comparator<E> c) {
        int len = endIndex - fromIndex;
        int minParentNodeIndex = len / 2 + fromIndex;
        for (int i = minParentNodeIndex; i >= 1; i--) {
            adjustNote(array, i, len, c);
        }
    }

    /**
     * 调整节点
     * @param array
     * @param parentNodeIndex
     * @param len
     * @param c
     * @version 1.0.0.0 2012-5-11 下午12:37:43
     */
    private void adjustNote(E[] array, int parentNodeIndex, int len, Comparator<E> c) {
        int selfChildIndex = parentNodeIndex * 2;
        int rightChildIndex = parentNodeIndex * 2 + 1;
        int maxNodeIndex = parentNodeIndex;
        
        if (selfChildIndex <= len) {
            if (c.compare(array[selfChildIndex - 1], array[maxNodeIndex - 1]) > 0) {
                maxNodeIndex = selfChildIndex;
            }
        }
        
        if (rightChildIndex <= len) {
            if (c.compare(array[rightChildIndex - 1], array[maxNodeIndex - 1]) > 0) {
                maxNodeIndex = rightChildIndex;
            }
        }
        
        if (maxNodeIndex != parentNodeIndex) {
            swap(array, maxNodeIndex - 1, parentNodeIndex - 1);
            if (maxNodeIndex < (len / 2)) {
                adjustNote(array, maxNodeIndex, len, c);
            }
        }
    }

}
