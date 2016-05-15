/*
 * $Header: ExceptionUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015年9月20日 下午4:40:31
 * $Owner: will
 */
package com.will.toolkit.util;

import java.lang.reflect.InvocationTargetException;

/**
 * ExceptionUtil 异常工具类
 * @author will
 * @version 1.0.0.0 2015年9月20日 下午4:40:31
 */
public final class ExceptionUtil {
    private ExceptionUtil() {}
    
    /**
     * 跑出一个异常
     * @param fmt
     * @param args
     * @return
     * @version 1.0.0.0 2015年9月20日 下午4:41:43
     */
    public static RuntimeException makeThrow(String fmt, Object... args) {
        return new RuntimeException(String.format(fmt, args));
    }

    /**
     * 包裹一个异常
     * @param t
     * @return
     * @version 1.0.0.0 2015年9月20日 下午4:42:43
     */
    public static RuntimeException wrapThrow(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        if (t instanceof InvocationTargetException)
            return wrapThrow(((InvocationTargetException) t).getTargetException());
        return new RuntimeException(t);
    }
    
    /**
     * 将抛出对象包裹成运行时异常，并增加自己的描述
     * 
     * @param e 抛出对象
     * @param fmt 格式
     * @param args 参数
     * 
     * @return 运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e, String fmt, Object... args) {
        return new RuntimeException(String.format(fmt, args), e);
    }
}
