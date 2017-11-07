package com.dragon.basic.java.lang.thread.join.interview;

public class MainTest {
	
	/**
	 * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
	 * @param args
	 */
	public static void main(String[] args) {
		T1 t1 = new T1();
		T2 t2 = new T2(t1);
		T3 t3 = new T3(t2);
		t1.start();
		t2.start();
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		for(StackTraceElement stack : stacks) {
			System.out.println(stack);
		}
		System.out.println("\n");
		t3.start();
	}
	
	// 执行结果就如同顺序执行一样，Thread中的join()方法的作用是：等待该线程终止，即执行完该线程才会往下执行
	/*T1 Thread start.
	T1 Thread look at 0
	T1 Thread look at 1
	T1 Thread look at 2
	T1 Thread look at 3
	T1 Thread look at 4
	T1 Thread end.
	T2 Thread start.
	T2 Thread look at 0
	T2 Thread look at 1
	T2 Thread look at 2
	T2 Thread look at 3
	T2 Thread look at 4
	T2 Thread end.
	T3 Thread start.
	T3 Thread end.*/

}