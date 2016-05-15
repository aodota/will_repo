/*
 * $Header: ShellSort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 下午12:15:01
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

import java.util.Comparator;

/**
 * ShellSort 希尔排序
 * @author will
 * @version 1.0.0.0 2012-5-11 下午12:15:01
 */
public class ShellSort<E extends Comparable<E>> extends Sort<E> {

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
        int step = initialStep(endIndex - fromIndex);
        for (; step >= 1; step = (step + 1) / 2 - 1) {
            for (int groupIndex = 0; groupIndex < step; groupIndex++) {
                insertSort(array, groupIndex, step, endIndex, c);
            }
        }
    }

    /**
     * 插入排序
     * @param array
     * @param groupIndex
     * @param step
     * @param end
     * @param c
     */
    private void insertSort(E[] array, int groupIndex, int step, int end, Comparator<E> c) {
        int startIndex = groupIndex;
        int endIndex = startIndex;
        
        while((endIndex + step) < end) {
            endIndex += step;
        }
        
        for (int i = startIndex; i <= endIndex; i = i + step) {
            for (int j = startIndex; j <= i; j = j + step) {
                E insertElement = array[i];
                if (c.compare(array[j], array[i]) > 0) {
                    move(array, j, i, step);
                    array[j] = insertElement;
                    break;
                }
            }
        }
    }

    /**
     * 初始化步长
     * @param len
     * @return
     * @version 1.0.0.0 2012-5-11 下午12:16:28
     */
    private int initialStep(int len) {
        /**
         * 步长应该按照下列公式
         * 1, 3, 7, 2 * (k-1), 2 * k - 1
         */
        int step = 1;
        while ((step + 1) * 2 - 1 < len - 1) {
            step = (step + 1) * 2 - 1;
        }
        
        return 0;
    }

}
