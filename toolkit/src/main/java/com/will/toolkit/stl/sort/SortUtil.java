/*
 * $Header: SortUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 下午12:47:28
 * $Owner: will
 */
package com.will.toolkit.stl.sort;


import com.will.toolkit.stl.Tuple;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;


/**
 * SortUtil
 * @author will
 * @version 1.0.0.0 2012-5-11 下午12:47:28
 */
public class SortUtil {
    
    /**
     * 排序Map
     * @param <E>
     * @param <K>
     * @param type
     * @param map
     * @return
     * @version 1.0.0.0 2012-5-11 下午12:48:40
     */
    public static <E extends Comparable<E>, K> Map<K, E> sortMap(Class<E> type, Map<K, E> map) {
        return sortMapReverse(type, map, true);
    }
    
    /**
     * 排序Map
     * @param <E>
     * @param <K>
     * @param type
     * @param map
     * @return
     * @version 1.0.0.0 2012-5-11 下午12:48:40
     */
    public static <E extends Comparable<E>, K> Map<K, E> sortMapReverse(Class<E> type, Map<K, E> map) {
        return sortMapReverse(type, map, false);
    }
    
    
    /**
     * 排序Map
     * @param <E>
     * @param <K>
     * @param type
     * @param map
     * @param order
     * @return
     * @version 1.0.0.0 2012-5-11 下午12:50:20
     */
    @SuppressWarnings("unchecked")
    private static <E extends Comparable<E>, K> Map<K, E> sortMapReverse(Class<E> type, Map<K, E> map, boolean order) {
        Map<K, E> resultMap = new LinkedHashMap<K, E>();
        HeapSort<E> sort = new HeapSort<E>();
        Tuple<Map<E, List<K>>, List<E>> tuple = getReverseMap(map);
        E[] array = (E[]) Array.newInstance(type, 0);
        array = tuple.right.toArray(array);
        
        if (order) {
            sort.sort(array);
        } else {
            sort.sortReverse(array);
        }
        
        E lastInsertElement = null;
        int index = 1;
        for (E e : array) {
            if (null == lastInsertElement || !lastInsertElement.equals(e)) {
                lastInsertElement = e;
                index = 1;
                resultMap.put(tuple.left.get(e).get(0), e);
            } else {
                resultMap.put(tuple.left.get(e).get(index), e);
                index++;
            }
        }
        
        return resultMap;
    }
    
    /**
     * 获得一个反转Map
     * @param <E>
     * @param <K>
     * @param map
     * @return
     * @version 1.0.0.0 2012-5-11 下午01:00:15
     */
    private static <E extends Comparable<E>, K> Tuple<Map<E, List<K>>, List<E>> getReverseMap(Map<K, E> map) {
        Tuple<Map<E, List<K>>, List<E>> tuple = new Tuple<Map<E,List<K>>, List<E>>();
        Map<E, List<K>> resultMap = new HashMap<E, List<K>>();
        List<E> resultList = new ArrayList<E>();
        
        Set<Entry<K, E>> entrySet = map.entrySet();
        for (Entry<K, E> entry : entrySet) {
            resultList.add(entry.getValue());
            if (resultMap.containsKey(entry.getValue())) {
                resultMap.get(entry.getValue()).add(entry.getKey());
            } else {
                List<K> list = new ArrayList<K>();
                list.add(entry.getKey());
                resultMap.put(entry.getValue(), list);
            }
        }
        
        tuple.left = resultMap;
        tuple.right = resultList;
        
        return tuple;
    }
}
