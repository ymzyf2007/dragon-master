package com.dragon.basic.java.util.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Java 信号量（Semaphore）
 * Semaphore控制某个资源可以被同时访问的线程个数，通过acquire()获取一个许可，如果没有就等待，而release()释放一个许可。
 * 单个信号量的Semaphore对象可以实现互斥锁的功能
 *
 */
public class SemaphoreTest {
	
	public static void main(String[] args) {
		// 线程池
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 只能5个线程同时访问
		final Semaphore semaphore = new Semaphore(5);
		// 模拟20个客户端访问
		for(int i = 0; i < 20; i++) {
			final int no = i;
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();
						System.out.println("Accessing: " + no);
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semaphore.release();
						System.out.println("-----------------" + semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}