/*
 * $Header: DataUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-6-27 下午03:40:46
 * $Owner: will
 */
package com.will.toolkit.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * 工具类，用于反射
 * @author will
 * @version 1.0.0.0 2012-6-27 下午03:40:46
 */
public class BeanUtil {
    public static void setObject(Object target, String property, Object value) throws SQLException {
        PropertyDescriptor[] props = propertyDescriptors(target.getClass());
        for (PropertyDescriptor prop : props) {
            if (prop.getName().equalsIgnoreCase(property)) {
                // 找到了
                callSetter(target, prop, value);
                return;
            }
        }
        
    }
    
    /**
     * 调用Setter
     * @param target 需要填充的Object
     * @param prop 填充的属性
     * @param value 填充的值
     * @throws SQLException
     * @version 1.0.0.0 2011-9-13 下午05:06:38
     */
    private static void callSetter(Object target, PropertyDescriptor prop, Object value) throws SQLException {
        Method setter = prop.getWriteMethod();
        if (setter == null) {
            return;
        }

        Class<?>[] params = setter.getParameterTypes();
        try {
            // convert types for some popular ones
            if (value != null) {
                if (value instanceof java.util.Date) {
                    if (params[0].getName().equals("java.sql.Date")) {
                        value = new java.sql.Date(((java.util.Date) value).getTime());
                    } else if (params[0].getName().equals("java.sql.Time")) {
                        value = new java.sql.Time(((java.util.Date) value).getTime());
                    } else if (params[0].getName().equals("java.sql.Timestamp")) {
                        value = new java.sql.Timestamp(((java.util.Date) value).getTime());
                    }
                }
            }

            // Don't call setter if the value object isn't the right type
            if (isCompatibleType(value, params[0])) {
                setter.invoke(target, new Object[] { value });
            } else {
                throw new RuntimeException("Cannot set " + prop.getName() + ": incompatible types.");
            }

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot set " + prop.getName() + ": " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot set " + prop.getName() + ": " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot set " + prop.getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * 是否是兼容的类型
     * @param value 填充的值
     * @param type 类型
     * @return
     * @version 1.0.0.0 2011-9-13 下午05:07:13
     */
    private static boolean isCompatibleType(Object value, Class<?> type) {
        // Do object check first, then primitives
        if (value == null || type.isInstance(value)) {
            return true;
        } else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value)) {
            return true;
        } else if (type.equals(Long.TYPE) && Long.class.isInstance(value)) {
            return true;
        } else if (type.equals(Double.TYPE) && Double.class.isInstance(value)) {
            return true;
        } else if (type.equals(Float.TYPE) && Float.class.isInstance(value)) {
            return true;
        } else if (type.equals(Short.TYPE) && Short.class.isInstance(value)) {
            return true;
        } else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value)) {
            return true;
        } else if (type.equals(Character.TYPE) && Character.class.isInstance(value)) {
            return true;
        } else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 获取PropertyDescriptor集合类
     * @param c Class类型
     * @return
     * @throws SQLException
     * @version 1.0.0.0 2011-9-13 下午05:08:14
     */
    private static PropertyDescriptor[] propertyDescriptors(Class<?> c) {
        // Introspector caches BeanInfo classes for better performance
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(c);
        } catch (IntrospectionException e) {
            throw new RuntimeException("Bean introspection failed: " + e.getMessage());
        }

        return beanInfo.getPropertyDescriptors();
    }
}
