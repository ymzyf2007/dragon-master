package com.dragon.basic.java.lang.thread.deadlock;

public class DeadLockTest {
	public static void main(String[] args) {
		Resource r0 = new Resource();
		Resource r1 = new Resource();
		r0.setFlag(0);
		r1.setFlag(1);
		Thread thread0 = new Thread(r0);
		Thread thread1 = new Thread(r1);
		thread0.setName("DeadLockThread-0 ");
		thread1.setName("DeadLockThread-1 ");
		thread0.start();
		thread1.start();
	}
}

class Resource implements Runnable {
	private int flag = 0;
	private static Object o1 = new Object(); // 定义静态对象
	private static Object o2 = new Object(); // 定义静态对象

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		if (0 == flag) {
			synchronized (o1) { // 获取对象o1的锁
				try {
					System.out.println("获取对象o1锁，并休眠4秒");
					Thread.sleep(4000); // 线程休眠4秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o2) { // 获取对象o2的锁
					System.out.println("do something...");
				}
			}
		}
		if (1 == flag) {
			synchronized (o2) { // 获取对象o2的锁
				try {
					System.out.println("获取对象o2锁，并休眠4秒");
					Thread.sleep(4000); // 线程休眠4秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o1) {
					System.out.println("do something2...");
				}
			}
		}
	}
}