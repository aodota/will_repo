/*
 * $Header: InsertSort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 上午11:57:24
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

import java.util.Comparator;

/**
 * QuickSort 快速排序
 * @author will
 * @version 1.0.0.0 2012-5-11 上午11:57:24
 */
public class QuickSort<E extends Comparable<E>> extends Sort<E> {

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
        quickSort(array, fromIndex, endIndex - 1, c);
    }

    /**
     * 快速排序
     * @param array
     * @param low
     * @param high
     */
    private void quickSort(E[] array, int low, int high, Comparator<E> c) {
        /*
         * 已low为基准，比自己大的移动到右边，比自己小的移动到左边
         */
        if (low >= high) {
            return;
        }

        int p = partition(array, low, high, c);
        quickSort(array, low, p - 1, c);
        quickSort(array, p + 1, high, c);
    }

    /**
     * 分组
     * @param array
     * @param low
     * @param high
     * @param c
     * @return
     */
    private int partition(E[]array, int low, int high, Comparator<E> c) {
        int i = low;
        int j = high;
        E base = array[low];
        while (i < j) {
            while (i < high && c.compare(array[i], base) <= 0) {
                i++;
            }
            while (j > low && c.compare(array[j], base) >= 0) {
                j--;
            }
            if (i < j) {
                swap(array, i, j);
            }
        }
        if (j != low) {
            swap(array, low, j);
        }
        return j;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[] { -5, 88,9,5,1,4,1,-1,0,81,2,65,12,520,0,-23 };
        QuickSort<Integer> sort = new QuickSort<Integer>();
        sort.sort(array);
        sort.print(array);
        
    }

}
