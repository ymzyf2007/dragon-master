package com.dragon.basic.java.lang.thread.volatilefield;

import java.util.concurrent.atomic.AtomicInteger;

//1
//public synchronized void increase() {
//	inc++;
//}

//2
//Lock lock = new ReentrantLock();
//public void increase() {
//    lock.lock();
//    try {
//        inc++;
//    } finally{
//        lock.unlock();
//    }
//}

public class Test {
	
	private AtomicInteger inc = new AtomicInteger();
	
	public void increase() {
		inc.getAndIncrement();
	}
	
	public static void main(String[] args) {
		final Test test = new Test();
		for(int i = 0; i < 10; i++) {
			new Thread() {
				@Override
				public void run() {
					for(int j = 0; j < 1000; j++) {
						test.increase();
					}
				}
			}.start();
		}
			
//		while(Thread.activeCount() > 1) {	// 保证前面的线程都执行完
//			Thread.yield();	// 暂停当前正在执行的线程对象，并执行其他线程。
//		}
		
		System.out.println(test.inc);
	}

}