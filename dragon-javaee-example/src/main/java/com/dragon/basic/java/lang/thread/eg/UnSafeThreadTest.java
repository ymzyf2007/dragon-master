package com.dragon.basic.java.lang.thread.eg;

/**
 * 线程不安全测试
 *
 */
public class UnSafeThreadTest {
	
	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			Count c = new Count();
			@Override
			public void run() {
				// 解决方法：2. 将线程类成员变量拿到run方法中，这时count引用是线程内的局部变量
//				Count c = new Count();
				c.count();
			}
		};
		
		for(int i = 0; i < 10; i++) {
			new Thread(runnable).start();
		}
	}

}

class Count {
//	private int num;
	
	public void count() {
		// 解决方法：1. 将Count中num变成count方法的局部变量；
		int num = 0;
		for(int i = 1; i <= 100; i++) {
			num += i;
		}
		System.out.println(Thread.currentThread().getName() + "-" + num);
	}
}