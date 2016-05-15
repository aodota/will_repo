/*
 * $Header: ObjectFactory.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-19 上午10:31:32
 * $Owner: will
 */
package com.will.toolkit.lang;

/**
 * 对象工厂类, 用于创建类
 * @author will
 * @version 1.0.0.0 2011-7-19 上午10:31:32
 *
 */
public class ObjectFactory {
	/**
	 * 创建一个类
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @version 1.0.0.0 2011-7-19 上午10:32:26
	 */
    public Object buildBean(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        Object o = clazz.newInstance();
        return o;
    }
}
