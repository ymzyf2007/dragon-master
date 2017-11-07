package com.dragon.basic.java.lang.thread.join.interview;

public class T3 extends Thread {
	
	private T2 t2;
	
	public T3(T2 t2) {
		super("T3 Thread");
		this.t2 = t2;
	}

	@Override
	public void run() {
		try {
			t2.join();	// 等待该线程终止
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + " start.");
			System.out.println(threadName + " end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}