package com.dragon.basic.java.lang.thread.threadlocal;

/**
 * 应用场景：在同一线程中方法与方法，类与类之间的共享内容传输
 *
 */
public class ThreadLocalTest {
	
//	private static final ThreadLocal<Integer> threadLocalValue = new ThreadLocal<Integer>() {
//		@Override
//		protected Integer initialValue() {
//			return 0;
//		}
//	};
	
	private static int count = 0;
	
	static class MyThread implements Runnable {
		private int index;
		
		public MyThread(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			System.out.println("线程" + index);
//			System.out.println("线程" + index + "的初始值threadLocalValue：" + threadLocalValue.get());
			for(int i = 0; i < 10; i++) {
//				threadLocalValue.set(threadLocalValue.get() + i);
				++count;
			}
//			System.out.println("线程" + index + "的累加threadLocalValue：" + threadLocalValue.get());
			System.out.println("线程" + index + "的累加Value：" + count);
		}
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 5; i++) {
			new Thread(new MyThread(i)).start();
		}
	}

}