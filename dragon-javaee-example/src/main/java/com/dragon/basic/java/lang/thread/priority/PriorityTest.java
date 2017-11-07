package com.dragon.basic.java.lang.thread.priority;

/**
 * 优先级1--10；默认是5
 *
 */
public class PriorityTest {
	
	public static void main(String[] args) {
		ThreadA t1 = new ThreadA("t1");
		ThreadA t2 = new ThreadA("t2");
		t1.setPriority(1);
		t2.setPriority(10);
		
		t1.start();
		t2.start();
	}

}

// 输出
/*t2(10) loop 0
t2(10) loop 1
t2(10) loop 2
t2(10) loop 3
t2(10) loop 4
t1(1) loop 0
t1(1) loop 1
t1(1) loop 2
t1(1) loop 3
t1(1) loop 4*/

class ThreadA extends Thread {
	
	public ThreadA(String name) {
		super(name);
	}

	@Override
	public void run() {
		for(int i = 0; i < 5; i++) {
			System.out.println(getName() + "(" + getPriority() + ") loop " + i);
		}
	}
	
}