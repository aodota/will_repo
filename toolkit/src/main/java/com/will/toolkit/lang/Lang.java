/*
 * $Header: Lang.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-19 上午10:05:39
 * $Owner: will
 */
package com.will.toolkit.lang;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Clob;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.NClob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author will
 * @version 1.0.0.0 2011-7-19 上午10:05:39
 * 
 */
public final class Lang {
    private Lang() {
    };

    /** DEFAULT_VALUE_MAP */
    private static Map<Class<?>, Object> DEFAULT_VALUE_MAP = new HashMap<Class<?>, Object>();

    /** DEFAULT_WRAPPERCLASS_MAP */
    private static Map<Class<?>, Class<?>> DEFAULT_WRAPPERCLASS_MAP = new HashMap<Class<?>, Class<?>>();

    /** FIELD_MAP */
    private static Map<Class<?>, MyField[]> FIELD_MAP = new HashMap<Class<?>, MyField[]>(1024);
    
    /** comObjectFactory */
    private static ObjectFactory comObjectFactory = new ObjectFactory();
    
    /** springObjectFactory */
    private static SpringObjectFactory springObjectFactory = null;

    static {
        DEFAULT_VALUE_MAP.put(boolean.class, false);
        DEFAULT_VALUE_MAP.put(byte.class, 0);
        DEFAULT_VALUE_MAP.put(char.class, 0);
        DEFAULT_VALUE_MAP.put(short.class, 0);
        DEFAULT_VALUE_MAP.put(int.class, 0);
        DEFAULT_VALUE_MAP.put(long.class, 0);
        DEFAULT_VALUE_MAP.put(float.class, 0);
        DEFAULT_VALUE_MAP.put(double.class, 0);

        DEFAULT_WRAPPERCLASS_MAP.put(boolean.class, Boolean.class);
        DEFAULT_WRAPPERCLASS_MAP.put(byte.class, Byte.class);
        DEFAULT_WRAPPERCLASS_MAP.put(char.class, Character.class);
        DEFAULT_WRAPPERCLASS_MAP.put(short.class, Short.class);
        DEFAULT_WRAPPERCLASS_MAP.put(int.class, Integer.class);
        DEFAULT_WRAPPERCLASS_MAP.put(long.class, Long.class);
        DEFAULT_WRAPPERCLASS_MAP.put(float.class, Float.class);
        DEFAULT_WRAPPERCLASS_MAP.put(double.class, Double.class);
    }

    /**
     * 获取类上的指定Annotation
     * 
     * @param <T>
     * @param clazz
     * @param anClass
     * @return
     * @version 1.0.0.0 2011-7-19 上午10:17:13
     */
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> anClass) {
        Class<?> cc = clazz;
        T annotation = null;
        while (null != cc && cc != Object.class) {
            annotation = cc.getAnnotation(anClass);
            if (null != annotation) {
                return annotation;
            }
            cc = cc.getSuperclass();
        }
        return null;
    }

    /**
     * 判断Field上是否包含指定Annotation
     * @param field
     * @param anClass
     * @return
     */
    public static boolean hasAnnotation(Field field, Class<? extends Annotation> anClass) {
        Annotation annotation = field.getAnnotation(anClass);
        return null != annotation;
    }

    /**
     * 判断一个方式是否是静态方法
     * 
     * @param method
     * @return
     * @version 1.0.0.0 2011-7-19 上午11:44:08
     */
    public static boolean isStaticMethod(Method method) {
        return Modifier.isStatic(method.getModifiers()) && !Modifier.isFinal(method.getModifiers());
    }

    /**
     * @param src
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午01:41:07
     */
    @SuppressWarnings("unchecked")
    public static <T> T castTo(Object src, Class<T> clazz) {
        if (null == src) {
            return (T) Lang.getDefaultValue(clazz);
        }

        return castTo(src, src.getClass(), clazz);
    }

    /**
     * 将src从原始类型转换为目标类型
     * 
     * @param <T>
     * @param <F>
     * @param src
     * @param fromType
     * @param toType
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:12:47
     */
    @SuppressWarnings("unchecked")
    public static <T, F> T castTo(Object src, Class<F> fromType, Class<T> toType) {
        if (fromType.getName().equals(toType.getName())) {
            return (T) src;
        } else if (toType.isAssignableFrom(fromType)) {
            return (T) src;
        }

        if (fromType == String.class) {
            return String2Object((String) src, toType);
        }

        if (fromType.isArray() && !toType.isArray()) {
            return castTo(Array.get(src, 0), toType);
        }

        if (fromType.isArray() && toType.isArray()) {
            int len = Array.getLength(src);
            Object result = Array.newInstance(toType.getComponentType(), len);
            for (int i = 0; i < len; i++) {
                Array.set(result, i, castTo(Array.get(src, i), toType.getComponentType()));
            }

            return (T) result;
        }

        return (T) Lang.getDefaultValue(toType);
    }

    /**
     * 将Str类型转换为指定类型
     * 
     * @param <T>
     * @param str
     * @param type
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:21:37
     */
    @SuppressWarnings("unchecked")
    public static <T> T String2Object(String str, Class<T> type) {
        try {
            if (isBoolean(type)) {
                return (T) Boolean.valueOf(str);
            } else if (isByte(type)) {
                return (T) Byte.valueOf(str);
            } else if (isChar(type)) {
                return (T) Character.valueOf(str.charAt(0));
            } else if (isInteger(type)) {
                return (T) Integer.valueOf(str);
            } else if (isFloat(type)) {
                return (T) Float.valueOf(str);
            } else if (isLong(type)) {
                return (T) Long.valueOf(str);
            } else if (isDouble(type)) {
                return (T) Double.valueOf(str);
            } else if (isShort(type)) {
                return (T) Short.valueOf(str);
            } else if (isString(type)) {
                return (T) str;
            } else if (isStringLike(type)) {
                return (T) str;
            } else {
                Constructor<T> constructor = (Constructor<T>) Lang.getWrapper(type).getConstructor(String.class);
                if (null != constructor) {
                    return constructor.newInstance(str);
                }
            }
        } catch (Throwable t) {
            // 不处理
        }

        return (T) Lang.getDefaultValue(type);
    }

    /**
     * 获得包装类
     * 
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:42:08
     */
    private static Class<?> getWrapper(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return DEFAULT_WRAPPERCLASS_MAP.get(clazz);
        }
        return clazz;
    }

    /**
     * 是否是布尔型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isBoolean(Class<?> clazz) {
        return is(clazz, boolean.class) || is(clazz, Boolean.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isByte(Class<?> clazz) {
        return is(clazz, byte.class) || is(clazz, Byte.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isChar(Class<?> clazz) {
        return is(clazz, char.class) || is(clazz, Character.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isShort(Class<?> clazz) {
        return is(clazz, short.class) || is(clazz, Short.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isInteger(Class<?> clazz) {
        return is(clazz, int.class) || is(clazz, Integer.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isLong(Class<?> clazz) {
        return is(clazz, long.class) || is(clazz, Long.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isFloat(Class<?> clazz) {
        return is(clazz, float.class) || is(clazz, Float.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isDouble(Class<?> clazz) {
        return is(clazz, double.class) || is(clazz, Double.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isString(Class<?> clazz) {
        return is(clazz, String.class);
    }

    /**
     * 是否是指定类型
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:29:21
     */
    public static boolean isStringLike(Class<?> clazz) {
        return CharSequence.class.isAssignableFrom(clazz);
    }

    /**
     * 判断两个是否为同一类型
     * 
     * @param clazz1
     * @param clazz2
     * @return
     * @version 1.0.0.0 2011-7-19 下午02:26:16
     */
    private static boolean is(Class<?> clazz1, Class<?> clazz2) {
        return clazz1 == clazz2;
    }

    /**
     * @param type
     * @return
     * @version 1.0.0.0 2011-7-19 下午01:44:57
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return DEFAULT_VALUE_MAP.get(clazz);
        }
        return null;
    }

    /**
     * 获取一个Clazz的Fields
     * 
     * @param clazz
     * @return
     * @version 1.0.0.0 2012-7-25 下午06:10:56
     */
    public static MyField[] getFields(Class<?> clazz) {
        MyField[] myFields = FIELD_MAP.get(clazz);
        if (null == myFields) {
            synchronized (FIELD_MAP) {
                myFields = FIELD_MAP.get(clazz);
                if (null == myFields) {
                    try {
                        BeanInfo bi = Introspector.getBeanInfo(clazz);
                        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
                        Map<String, PropertyDescriptor> pdMap = new HashMap<String, PropertyDescriptor>();
                        for (PropertyDescriptor pd : pds) {
                            if (pd.getPropertyType() == Class.class) {
                                continue;
                            }

                            pdMap.put(pd.getDisplayName(), pd);
                        }
                        
                        Field[] fields = clazz.getDeclaredFields();
                        List<MyField> list = new ArrayList<MyField>();
                        for (Field field : fields) {
                            PropertyDescriptor pd = pdMap.get(field.getName());
                            if (null == pd) {
                                continue;
                            }
                            
                            MyField myField = new MyField();
                            myField.fieldName = pd.getDisplayName();
                            myField.field = field;
                            myField.classType = getType(pd.getPropertyType());
                            myField.getter = pd.getReadMethod();
                            myField.writter = pd.getWriteMethod();
                            list.add(myField);
                        }

                        myFields = list.toArray(new MyField[0]);
                    } catch (IntrospectionException e) {
                        e.printStackTrace();
                    }
                    FIELD_MAP.put(clazz, myFields);
                }
            }
        }

        return myFields;
    }

    /**
     * 获取ClassType
     * 
     * @param clazzType
     * @return
     * @version 1.0.0.0 2012-7-25 下午06:22:04
     */
    private static ClassType getType(Class<?> clazzType) {
        ClassType type = ClassType.PRIMITIVE_TYPE;
        if (clazzType.isAssignableFrom(Date.class)) {
            type = ClassType.DATE_TYPE;
        } else if (clazzType.isAssignableFrom(Map.class)) {
            type = ClassType.MAP_TYPE;
        } else if (clazzType.isAssignableFrom(List.class)) {
            type = ClassType.LIST_TYPE;
        } else if (clazzType.isArray()) {
            type = ClassType.ARRAY_TYPE;
        }

        return type;
    }

    /** Field的包裹 */
    public static class MyField {
        public Field field;
        public String fieldName;
        public ClassType classType;
        public Method getter;
        public Method writter;
    }

    /** 类的类型 */
    public enum ClassType {
        PRIMITIVE_TYPE, STATIC_TYPE, FINAL_TYPE, DATE_TYPE, MAP_TYPE, LIST_TYPE, ARRAY_TYPE
    }

    /**
     * 获取类的Jdbc类型
     * 
     * @param clazzType
     * @return
     * @version 1.0.0.0 2012-7-26 下午04:19:07
     */
    public static Type getJdbcType(Class<?> clazzType) {
        Type type = Type.Object;
        if (isInteger(clazzType)) {
            type = Type.Int;
        } else if (isLong(clazzType)) {
            type = Type.Long;
        } else if (isDouble(clazzType)) {
            type = Type.Double;
        } else if (isFloat(clazzType)) {
            type = Type.Float;
        } else if (isStringLike(clazzType)) {
            type = Type.String;
        } else if (isByte(clazzType)) {
            type = Type.Byte;
        } else if (isShort(clazzType)) {
            type = Type.Int;
        } else if (isChar(clazzType)) {
            type = Type.Int;
        } else if (is(clazzType, Date.class)) {
            type = Type.SqlDate;
        } else if (is(clazzType, java.util.Date.class)) {
            type = Type.Date;
        } else if (is(clazzType, java.sql.Time.class)) {
            type = Type.Time;
        } else if (is(clazzType, java.sql.Timestamp.class)) {
            type = Type.Timestamp;
        } else if (clazzType.isAssignableFrom(BigDecimal.class)) {
            type = Type.BigDecimal;
        } else if (clazzType.isAssignableFrom(Blob.class)) {
            type = Type.Blob;
        } else if (clazzType.isAssignableFrom(Clob.class)) {
            type = Type.Clob;
        } else if (clazzType.isAssignableFrom(NClob.class)) {
            type = Type.NClob;
        } else if (is(byte[].class, clazzType) || is(Byte[].class, clazzType)) {
            type = Type.Bytes;
        } else if (isBoolean(clazzType)) {
            type = Type.Bool;
        }
        
        return type;
    }

    /**
     * 设置SpringObjectFactory
     * @param objectFactory
     */
    public static void setSpringObjectFactory(SpringObjectFactory objectFactory) {
        Lang.springObjectFactory = objectFactory;
    }

    /**
     * 创建实例
     * @param clazz
     * @return
     * @version 1.0.0.0 2012-7-26 下午05:03:11
     */
    public static Object createObject(Class<?> clazz) throws Exception {
        if (null != springObjectFactory) {
            return springObjectFactory.buildBean(clazz);
        }
        return comObjectFactory.buildBean(clazz);
    }
}
