package com.dragon.basic.java.lang.thread.producer_consumer.eg1;

import java.util.Random;

/**
 * wait()、notify()方式实现生产者-消费者模式
 *
 */
public class MainTest {
	
	private static Random random = new Random();
	
	public static void main(String[] args) {
		Storage storage = new Storage();
		for(int i = 1; i <= 10; i++) {
			new Thread(new Producer(storage, random.nextInt(60))).start();
		}
		
		for(int i = 1; i <= 3; i++) {
			new Thread(new Consumer(storage, random.nextInt(100))).start();
		}
	}

}