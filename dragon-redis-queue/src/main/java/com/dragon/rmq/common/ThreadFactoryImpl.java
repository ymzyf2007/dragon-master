package com.dragon.rmq.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程工厂实现类
 *
 */
public class ThreadFactoryImpl implements ThreadFactory {
	
	private final AtomicLong threadIndex = new AtomicLong(0);
	private final String threadNamefix;
	
	public ThreadFactoryImpl(final String threadNamefix) {
		this.threadNamefix = threadNamefix;
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, threadNamefix + threadIndex.incrementAndGet());
	}

}