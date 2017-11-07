package com.dragon.basic.java.lang.thread.synchronizedfield;

public class ThreadA implements Runnable {
	
	private MultiSynchronizedTest multiSync;
	
	public ThreadA(MultiSynchronizedTest multiSync) {
		this.multiSync = multiSync;
	}

	@Override
	public void run() {
		System.out.println("ThreadName: " + Thread.currentThread().getName());
		multiSync.test1();
	}

}