package com.dragon.basic.java.util.concurrent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor1 {
	
	private ThreadPoolExecutor threadpool;
	
	public TestThreadPoolExecutor1() {
		/**
		 * public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler);
		 * 
		 * corePoolSize - 池中所保存的线程数，包括空闲线程。
		 * maximumPoolSize - 池中允许的最大线程数。
		 * keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
		 * unit - keepAliveTime 参数的时间单位。
		 * workQueue - 执行前用于保持任务的队列。此队列仅由保持 execute 方法提交的 Runnable 任务。
		 * handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。 
		 */
		threadpool = new ThreadPoolExecutor(2, 10, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.DiscardOldestPolicy());
	}
	
	// 增加任务到线程池
	public void submit(final int flag) {
		threadpool.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("执行线程名称：" + Thread.currentThread().getName());
				// 当前线程休眠5秒，不释放锁
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println(flag + " Hello");
			}
		});
	}
	
	// close thread pool
	public void shutdown() {
		threadpool.shutdown();
	}
	
	/** 
     * 当一个任务通过execute(Runnable)方法欲添加到线程池时： 
     * 1.如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。 
     * 2.如果此时线程池中的数量等于corePoolSize，但是缓冲队列workQueue未满，那么任务被放入缓冲队列。 
     * 3.如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。 
     * 4.如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
     * 5.当线程池中的线程数量大于corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。 
     */  
	public static void main(String[] args) {
		TestThreadPoolExecutor1 t = new TestThreadPoolExecutor1();
		for(int i = 0; i < 20; i++) {
			t.submit(i);  
		}
		System.out.println("Main end...");
	}
	
	// 输出
//	执行线程名称：pool-1-thread-1
//	执行线程名称：pool-1-thread-3
//	Main end...
//	执行线程名称：pool-1-thread-2
//	执行线程名称：pool-1-thread-4
//	执行线程名称：pool-1-thread-6
//	执行线程名称：pool-1-thread-8
//	执行线程名称：pool-1-thread-10
//	执行线程名称：pool-1-thread-5
//	执行线程名称：pool-1-thread-7
//	执行线程名称：pool-1-thread-9
//	0 Hello
//	执行线程名称：pool-1-thread-1
//	12 Hello
//	执行线程名称：pool-1-thread-3
//	1 Hello
//	执行线程名称：pool-1-thread-2
//	13 Hello
//	执行线程名称：pool-1-thread-4
//	15 Hello
//	执行线程名称：pool-1-thread-6
//	17 Hello
//	执行线程名称：pool-1-thread-8
//	19 Hello
//	执行线程名称：pool-1-thread-10
//	18 Hello
//	16 Hello
//	执行线程名称：pool-1-thread-7
//	14 Hello
//	执行线程名称：pool-1-thread-5
//	执行线程名称：pool-1-thread-9
//	2 Hello
//	3 Hello
//	7 Hello
//	6 Hello
//	5 Hello
//	4 Hello
//	8 Hello
//	9 Hello
//	10 Hello
//	11 Hello


}