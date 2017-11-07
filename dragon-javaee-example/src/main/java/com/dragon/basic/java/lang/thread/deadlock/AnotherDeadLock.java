package com.dragon.basic.java.lang.thread.deadlock;

public class AnotherDeadLock {
	
	public static void main(String[] args) {
		final Object resource1 = "resource1";
		final Object resource2 = "resource2";
		
		// 线程1
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// Lock resource 1
				synchronized(resource1) {
					System.out.println("Thread 1: Locked resource 1");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// Lock resource 2
					synchronized(resource2) {
						System.out.println("Thread 1: Locked resource 2");
					}
				}
			}
		};
		
		// 线程2
		Thread t2 = new Thread() {
			@Override
			public void run() {
				// Lock resource 2
				synchronized(resource2) {
					System.out.println("Thread 2: Locked resource 2");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// Lock resource 1
					synchronized(resource1) {
						System.out.println("Thread 2: Locked resource 1");
					}
				}
			}
		};
		
		// 启动线程
		t1.start();
		t2.start();
	}

}