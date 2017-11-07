package com.dragon.basic.java.lang.thread.producer_consumer.eg1;

public class Consumer implements Runnable {
	
	private Storage storage;
	private int num;
	
	public Consumer(Storage storage, int num) {
		this.storage = storage;
		this.num = num;
	}

	@Override
	public void run() {
		storage.consumer(num);
	}

}