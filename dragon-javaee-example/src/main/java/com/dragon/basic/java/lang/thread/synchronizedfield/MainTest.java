package com.dragon.basic.java.lang.thread.synchronizedfield;

public class MainTest {
	
	public static void main(String[] args) {
		MultiSynchronizedTest multiSync = new MultiSynchronizedTest();
		new Thread(new ThreadA(multiSync)).start();
		new Thread(new ThreadB(multiSync)).start();
		
		while(Thread.activeCount() > 1) {
			
		}
		
	}

}