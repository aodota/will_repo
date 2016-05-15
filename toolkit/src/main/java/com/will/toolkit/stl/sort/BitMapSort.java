/*
 * $Header: BitMapSort.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 下午12:43:03
 * $Owner: will
 */
package com.will.toolkit.stl.sort;

/**
 * BitMapSort
 * @author will
 * @version 1.0.0.0 2012-5-11 下午12:43:03
 */
public class BitMapSort {
    /**
     * 排序
     * @param array
     * @return
     * @version 1.0.0.0 2012-5-11 下午12:46:36
     */
    public static int[] sort(int[] array) {
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (maxValue < array[i]) {
                maxValue = array[i];
            }
        }
        
        byte[] bitmap = new byte[maxValue + 1];
        for (int i = 0; i < array.length; i++) {
            bitmap[array[i]] = 1;
        }
        
        int[] result = new int[array.length];
        int index = 0;
        for (int i = maxValue; i >= 0 && index < array.length; --i) {
            if (bitmap[i] == 1) {
                result[index++] = i;
            }
        }
        
        return result;
    }
}
