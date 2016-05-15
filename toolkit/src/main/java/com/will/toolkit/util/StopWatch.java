/*
 * $Header: StopWatch.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-18 下午03:59:20
 * $Owner: will
 */
package com.will.toolkit.util;

/**
 * 一个简单的计时器
 * @author will
 * @version 1.0.0.0 2011-7-18 下午03:59:20
 * 
 */
public class StopWatch {
	private long start;
	private long end;

	public StopWatch() {
	};
	
	/**
	 * 获取秒表
	 * @return
	 * @version 1.0.0.0 2011-7-18 下午04:04:28
	 */
	public static StopWatch begin() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}
	
	/**
	 * 统计允许一个特定项目的时间
	 * @param runnable
	 * @return
	 * @version 1.0.0.0 2011-7-18 下午04:24:20
	 */
	public static long run(Runnable runnable) {
		StopWatch sw = begin();
		runnable.run();
		sw.stop();
		return sw.getElapsedTime();
	}

	/**
	 * 开始计时
	 * @version 1.0.0.0 2011-7-18 下午04:02:49
	 */
	public void start() {
		this.start = System.currentTimeMillis();
	}
	
	/**
	 * 结束计时
	 * 
	 * @version 1.0.0.0 2011-7-18 下午04:03:25
	 */
	public void stop() {
		this.end = System.currentTimeMillis();
	}
	
	/**
	 * 获取消耗时间
	 * @return
	 * @version 1.0.0.0 2011-7-18 下午04:04:07
	 */
	public long getElapsedTime() {
		return end - start;
	}
	
}
