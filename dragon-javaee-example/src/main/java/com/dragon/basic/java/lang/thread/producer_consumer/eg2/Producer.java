package com.dragon.basic.java.lang.thread.producer_consumer.eg2;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
	
	private BlockingQueue<Object> queue;
	
	public Producer(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		for(int i = 0; i < 100; i++) {
			try {
				System.out.println("Producer["+ i +"]");
				queue.put(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}