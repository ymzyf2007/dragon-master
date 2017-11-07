package com.dragon.basic.java.lang.thread.volatilefield;

/**
 * 下面看一个例子，我们实现一个计数器，每次线程启动的时候，会调用计数器inc方法，对计数器进行加一
 * 详细说明看CSDN博客：http://blog.csdn.net/ymzyf2007/article/details/50222767
 *
 */
public class Counter {
	
	// volatile只能保证可见性，不能保证原子性
	public volatile static int count = 0;
	
	public static void inc() {
		// 这里延迟1毫秒，使得结果明显
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		count++;
	}
	
	public static void main(String[] args) {
		// 同时启动1000个线程，去进行i++运算，看看实际结果
		for(int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				// 执行完线程中的所有代码后，线程就自动结束并自我销毁
				@Override
				public void run() {
					Counter.inc();
				}
			}).start();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Counter.count);
	}

}