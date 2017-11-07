package com.dragon.basic.java.lang.thread.wait;

class ThreadA extends Thread {
	
	public ThreadA(String name) {
		super(name);
	}

	@Override
	public void run() {
		synchronized(this) {	// 同步块，只有获取了互斥锁才能执行同步块代码
			System.out.println(Thread.currentThread().getName() + " call notify.");
			// 唤醒当前的wait线程
			notify();	// 唤醒等待区线程（wait和notify只能在同步块即有锁的地方出现，不然会报错；wait和notify都会释放锁）
		}
	}
	
}

/**
 * 多线程wait，notity
 *
 */
public class WaitTest extends Thread {
	
	public static void main(String[] args) {
		ThreadA t1 = new ThreadA("ThreadA");
		synchronized(t1) {	// 获取t1对象的同步锁
			try {
				System.out.println(Thread.currentThread().getName() + " start t1.");
				// t1线程启动
				t1.start();
				
				System.out.println(Thread.currentThread().getName() + " wait()");
				t1.wait();
				
				System.out.println(Thread.currentThread().getName()+" continue.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
//			try {
//				Thread.sleep(30000);	// 线程休眠30秒，从这就知道sleep不会释放锁
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
}

// 运行结果
/*main start t1.
main wait()
ThreadA call notify.
main continue.*/