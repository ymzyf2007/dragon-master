package com.dragon.basic.java.lang.thread.yield;

public class YieldTest {
	
	public static void main(String[] args) {
		ThreadB t1 = new ThreadB("t1");
		ThreadB t2 = new ThreadB("t2");
		t1.start();
		t2.start();
	}

}

class ThreadB extends Thread {
	
	public ThreadB(String name) {
		super(name);
	}

	@Override
	public void run() {
//		synchronized(this) {	// 这里不需要互斥锁，这里不涉及共享变量
			for(int i = 0; i < 10; i++) {
				System.out.printf("%s [%d]:%d\n", getName(), getPriority(), i);
				if(i % 4 == 0) {
					yield();	// 使线程从执行状态变为就绪状态，从而让其它具有相同优先级的等待线程获取执行权，但是并不保证其它线程一定能够获取执行权，也有可能该线程又获取到执行权
				}
			}
//		}
	}
	
}