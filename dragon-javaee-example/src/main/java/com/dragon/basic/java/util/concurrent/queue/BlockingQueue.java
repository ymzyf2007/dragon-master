package com.dragon.basic.java.util.concurrent.queue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue {
	
	private List<Object> queue = new LinkedList<Object>();	// 队列
	private int limit;	// 队列容量大小
	
	public BlockingQueue(int limit) {
		this.limit = limit;
	}
	
	/**
	 * 入队列
	 * @param o
	 * @throws InterruptedException 
	 */
	public synchronized void enqueue(Object o) throws InterruptedException {
		System.out.println("in:" + o);
		while(queue.size() == limit) {
			wait();
		}
		if(queue.size() == 0) {	// 【队列为空，加入元素的时候唤醒所有等待线程，这样加入的元素就可以立马消费】
			notifyAll();
		}
		System.out.println("enqueue add:" + o.toString());
		queue.add(o);
	}
	
	/**
	 * 出队列
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized Object dequeue() throws InterruptedException {
		System.out.println("out");
		while(queue.size() == 0) {
			wait();
		}
		if(queue.size() == limit) {	// 【队列满时，消费元素的时候唤醒所有等待线程，这样生产者线程又可以生产】
			notifyAll();
		}
		System.out.println("enqueue out:" + queue.get(0));
		return queue.remove(0);
	}

}