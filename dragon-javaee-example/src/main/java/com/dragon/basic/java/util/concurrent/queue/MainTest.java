package com.dragon.basic.java.util.concurrent.queue;

public class MainTest {
	
	public static void main(String[] args) {
		final BlockingQueue blockQueue = new BlockingQueue(10);
		// 生产者线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 100; i++) {
					try {
						blockQueue.enqueue("" + i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		// 消费者线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < 100; i++) {
					try {
						blockQueue.dequeue();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}