package com.dragon.basic.java.lang.thread.join.interview;

public class T1 extends Thread {
	
	public T1() {
		super("T1 Thread");
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " start.");
		
		try {
			for(int i = 0; i < 5; i++) {
				System.out.println(threadName + " look at " + i);
				Thread.sleep(1000);
			}
			
			System.out.println(threadName + " end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}