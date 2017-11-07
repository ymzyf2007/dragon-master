package com.dragon.basic.java.lang.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPoolTest {
	
	public static void main(String[] args) {
		// 创建一个可重用固定线程数的线程池，线程池中最多能同时运行2个线程
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		ThreadA t1 = new ThreadA("t1");
		ThreadA t2 = new ThreadA("t2");
		ThreadA t3 = new ThreadA("t3");
		ThreadA t4 = new ThreadA("t4");
		ThreadA t5 = new ThreadA("t5");
		
		// 将线程放入线程池中执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		
		// 关闭线程池
		pool.shutdown();
	}

}
// 执行结果
/*pool-1-thread-1 running.
pool-1-thread-2 running.
pool-1-thread-2 running.
pool-1-thread-2 running.
pool-1-thread-1 running.*/

class ThreadA extends Thread {
	
	public ThreadA(String name) {
		super(name);
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " running.");
	}
	
}