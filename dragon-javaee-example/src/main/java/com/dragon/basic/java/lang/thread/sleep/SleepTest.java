package com.dragon.basic.java.lang.thread.sleep;

public class SleepTest {
	
	public static void main(String[] args) {
		ThreadA t1 = new ThreadA("t1");
		t1.start();
	}

}

class ThreadA extends Thread {
	
	public ThreadA(String name) {
		super(name);
	}

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.printf("%s [%d]:%d\n", getName(), getPriority(), i);
			if(i % 4 == 0) {
				try {
					Thread.sleep(3000);	// 当前线程休眠3s，但是不释放锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}