package com.dragon.basic.java.lang.thread.join;

public class BThread extends Thread {
	
	public BThread() {
		super("[BThread] Thread");
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " start.");
		
		try {
			for(int i = 0; i < 5; i++) {
				System.out.println(threadName + " loop at " + i);
				Thread.sleep(1000);
			}
			System.out.println(threadName + " end.");
		} catch (InterruptedException e) {
			System.out.println("Exception from " + threadName + " run.");
		}
	}

}