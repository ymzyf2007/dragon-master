package com.dragon.basic.java.lang.thread.join;

public class TestDemo {

	/**
	 * 二、为什么要用join()方法
	 * 在很多情况下，主线程生成并起动了子线程，如果子线程里要进行大量的耗时的运算，主线程往往将于子线程之前结束，
	 * 但是如果主线程处理完其他的事务后，需要用到子线程的处理结果，也就是主线程需要等待子线程执行完成之后再结束，这个时候就要用到join()方法了。
	 * @param args
	 */
	public static void main(String[] args) {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " start.");
		
		BThread bt = new BThread();
		AThread at = new AThread(bt);
		try {
			bt.start();
			Thread.sleep(2000);
			at.start();
			at.join();	// 等待该线程终止。 
		} catch (Exception e) {
			System.out.println("Exception from main");
		}
		System.out.println(threadName + " end!");
	}
	
	/**
	 * main start.	// 主线程起动，因为调用了at.join()，要等到at结束了，此线程才能向下执行。
	 * [BThread] Thread start.	// 线程bt起动
	 * [BThread] Thread loop at 0
	 * [BThread] Thread loop at 1
	 * [BThread] Thread loop at 2
	 * [AThread] Thread start.	// 线程at启动，因为调用bt.join()，等到bt结束了才向下执行。
	 * [BThread] Thread loop at 3
	 * [BThread] Thread loop at 4
	 * [BThread] Thread end.
	 * [AThread] Thread end.
	 * main end!
	 */
	
	// 注释main方法的join执行如下
	/**
	 * main start.    // 主线程起动，因为Thread.sleep(2000)，主线程没有马上结束;
	 * [BThread] Thread start.    // 线程BThread起动
	 * [BThread] Thread loop at 0
	 * [BThread] Thread loop at 1
	 * main end!   // 在sleep两秒后主线程结束，AThread执行的bt.join();并不会影响到主线程。
	 * [AThread] Thread start.    // 线程at起动，因为调用了bt.join()，等到bt结束了，此线程才向下执行。
	 * [BThread] Thread loop at 2
	 * [BThread] Thread loop at 3
	 * [BThread] Thread loop at 4
	 * [BThread] Thread end.    // 线程BThread结束了
	 * [AThread] Thread end.    // 线程AThread在bt.join();阻塞处起动，向下继续执行的结果
	 */

}