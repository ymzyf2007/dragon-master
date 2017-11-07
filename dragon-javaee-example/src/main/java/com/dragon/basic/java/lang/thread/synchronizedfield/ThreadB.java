package com.dragon.basic.java.lang.thread.synchronizedfield;

public class ThreadB implements Runnable {
	
	private MultiSynchronizedTest multiSync;
	
	public ThreadB(MultiSynchronizedTest multiSync) {
		this.multiSync = multiSync;
	}

	@Override
	public void run() {
		System.out.println("ThreadName: " + Thread.currentThread().getName());
		multiSync.test2();
	}

}