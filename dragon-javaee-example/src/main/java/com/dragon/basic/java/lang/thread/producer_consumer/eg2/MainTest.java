package com.dragon.basic.java.lang.thread.producer_consumer.eg2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainTest {
	
	public static void main(String[] args) {
		BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
		
		new Thread(new Producer(queue)).start();
		new Thread(new Consumer(queue)).start();
	}

}