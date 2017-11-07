package com.dragon.basic.java.lang.thread.eg;

import java.util.concurrent.TimeUnit;

public class TimeoutTest {
	
	private long timeout = 10;
	private /*volatile*/ long lastTime = System.currentTimeMillis();
	
	public TimeoutTest(long timeout) {
		this.timeout = timeout;
		new Thread(new TimeoutThread()).start();
	}
	
	private void execute() {
		System.out.println("execute method...");
	}
	
	private class TimeoutThread implements Runnable {
		public void run() {
			while(true) {
				if((System.currentTimeMillis() - lastTime) >= timeout * 1000) {
					execute();
					lastTime = System.currentTimeMillis();
				} else {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TimeoutTest(5);
	}

}