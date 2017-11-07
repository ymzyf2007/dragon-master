package com.dragon.basic.java.lang.thread.join.interview;

public class T2 extends Thread {
	
	private T1 t1;
	
	public T2(T1 t1) {
		super("T2 Thread");
		this.t1 = t1;
	}

	@Override
	public void run() {
		try {
			t1.join();	// 等待该线程终止
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + " start.");
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