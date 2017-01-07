/*
 * $Header: PropertiesUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-9-28 下午04:23:40
 * $Owner: will
 */
package com.will.toolkit.util;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 从属性文件中读取内容
 * @author will
 * @version 1.0.0.0 2011-9-28 下午04:23:40
 *
 */
public class PropertiesUtil {
    /** 缓存Map，提高性能 */
    private static Map<String, ResourceBundle> cacheMap = new ConcurrentHashMap<String, ResourceBundle>();
    
    /** 分割符 */
    private static final String SPLIT = "-";
    
    /**
     * 构造函数
     */
    private PropertiesUtil() {
        
    }
    
    /**
     * 从资源文件中获取对应key值内容
     * @param clazz 从当前class对应包下开始搜索
     * @param key key值
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:36:37
     */
    public static Double getDoubleText(Class<?> clazz, String key) {
        try {
            return Double.parseDouble(getText(clazz, key));
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }
    
    /**
     * 从资源文件中获取对应key值内容
     * @param clazz 从当前class对应包下开始搜索
     * @param key key值
     * @param split 分隔符
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:36:37
     */
    public static String[] getStringArrayText(Class<?> clazz, String key, String split) {
        return getText(clazz, key).split(split);
    }
    
    /**
     * 从资源文件中获取对应key值内容
     * @param clazz 从当前class对应包下开始搜索
     * @param key key值
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:36:37
     */
    public static String getText(Class<?> clazz, String key) {
        return findText(clazz, key);
    }
    
    /**
     * 获取Key对应的值
     * @param clazz 类
     * @param key Key值
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:26:58
     */
    private static String findText(Class<?> clazz, String key) {
        String name = clazz.getName();
        String _key = getKey(name, key);
        ResourceBundle rb = cacheMap.get(_key);
        if (null != rb) {
            return rb.getString(key);
        }
        
        return findText(name, key, _key);
    }
    
    /**
     * 从资源文件中查找
     * @param className 类名
     * @param key key值
     * @param _key 缓存key值
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:28:56
     */
    private static String findText(String className, String key, String _key) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(className + ".package", Locale.getDefault(), Thread
                    .currentThread().getContextClassLoader());
            if (null != resourceBundle) {
                String value = resourceBundle.getString(key);
                cacheMap.put(_key, resourceBundle);
                return value;
            }
        } catch (Exception e) {
            // do nothing
        }
        
        while (className.indexOf(".") != -1) {
            // 如果当前目录没有找到，去上层目录的资源文件寻找
            className = className.substring(0, className.lastIndexOf("."));
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle(className + ".package", Locale.getDefault(), Thread
                        .currentThread().getContextClassLoader());
                if (null != resourceBundle) {
                    String value = resourceBundle.getString(key);
                    cacheMap.put(_key, resourceBundle);
                    return value;
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        
        // 最后在那个没找到
        return "";
    }

    /**
     * 获取缓存使用的Key值
     * @param className 类名
     * @param key key值
     * @return
     * @version 1.0.0.0 2011-9-28 下午04:29:00
     */
    private static String getKey(String className, String key) {
        return new StringBuilder(50).append(className).append(SPLIT).append(key).toString();
    }

    
}
