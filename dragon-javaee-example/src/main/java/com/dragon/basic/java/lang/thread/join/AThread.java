package com.dragon.basic.java.lang.thread.join;

public class AThread extends Thread {
	
	private BThread bt;
	
	public AThread(BThread bt) {
		super("[AThread] Thread");
		this.bt = bt;
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " start.");
		
		try {
			bt.join();	// 等待该线程终止。 
			System.out.println(threadName + " end.");
		} catch (InterruptedException e) {
			System.out.println("Exception from " + threadName + " run.");
		}
	}

}