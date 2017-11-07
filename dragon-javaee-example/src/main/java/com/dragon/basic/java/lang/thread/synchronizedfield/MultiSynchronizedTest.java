package com.dragon.basic.java.lang.thread.synchronizedfield;

public class MultiSynchronizedTest {
	
	public synchronized void test1() {
		System.out.println("test1 method entry");
		try {
			Thread.sleep(5000);	// 当前线程休眠5秒，sleep不释放锁
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test1 method out");
	}
	
	public /*synchronized */void test2() {
		System.out.println("test2 method entry");
		try {
			Thread.sleep(5000);	// 当前线程休眠5秒，sleep不释放锁
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("test2 method out");
	}

}