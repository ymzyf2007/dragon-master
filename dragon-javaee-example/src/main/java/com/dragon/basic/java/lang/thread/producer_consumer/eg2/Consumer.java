package com.dragon.basic.java.lang.thread.producer_consumer.eg2;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
	
	private BlockingQueue<Object> queue;

	public Consumer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("Consume["+ queue.take() +"]");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}