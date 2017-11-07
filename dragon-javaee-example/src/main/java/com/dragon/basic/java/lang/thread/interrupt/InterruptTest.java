package com.dragon.basic.java.lang.thread.interrupt;

public class InterruptTest {
	
	public static void main(String[] args) {
		try {
			ThreadA t1 = new ThreadA("t1");	// 创建线程1
			System.out.println(t1.getName() + " (" + t1.getState() + ") is new.");
			
			t1.start();	// 启动线程1
			System.out.println(t1.getName() + " (" + t1.getState() + ") is started.");
			
			// 主线程休眠300ms，然后给t1发中断指令
			Thread.sleep(300);
			t1.interrupt();
			System.out.println(t1.getName() + " (" + t1.getState() + ") is interrupted.");
			
			// 主线程休眠300ms，然后查看t1的状态
			Thread.sleep(300);
			System.out.println(t1.getName() + " (" + t1.getState() + ") is interrupted now.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class ThreadA extends Thread {
	
	public ThreadA(String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
			int i = 0;
			while(!isInterrupted()) {	// 测试线程是否已经中断。线程的中断状态 不受该方法的影响
				Thread.sleep(100);	// 让当前正在执行的线程休眠100ms
				i++;
				System.out.println(getName() + " (" + getState() + ") loop " + i);
			}
		} catch (InterruptedException e) {
			System.out.println(getName() + " (" + getState() + ") catch InterruptedException.");  
		}
	}
	
}