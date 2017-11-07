package com.dragon.basic.java.util.concurrent.map;

import java.util.HashMap;
import java.util.UUID;

public class ConcurrentHashMapTest {
	
	public static void main(String[] args) {
		try {
			final HashMap<String, String> map = new HashMap<String, String>();
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 10000; i++) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								 map.put(UUID.randomUUID().toString(), "");
							}
						}, "thread" + i).start();
					}
				}
			}, "ftf");
			
			t.start();
			t.join();	// 等待该线程终止
			System.out.println("main end...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}

}