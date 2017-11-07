package com.dragon.basic.java.lang.thread.producer_consumer.eg1;

import java.util.LinkedList;

/**
 * 仓库（共享变量）
 *
 */
public class Storage {
	
	// 仓库最大存储量
	public final int MAX_SIZE = 100;
	// 仓库存储的载体
	private LinkedList<Object> list = new LinkedList<Object>();
	
	public LinkedList<Object> getList() {
		return list;
	}
	
	public void setList(LinkedList<Object> list) {
		this.list = list;
	}
	
	/**
	 * 上产num个产品
	 * @param num
	 */
	public void producer(int num) {
		synchronized(list) {
			while(list.size() + num > MAX_SIZE) {
				System.out.println("【仓库最大存储量】:"+ MAX_SIZE +"/t【要生产的产品数量】:"+ num +"/t【库存量】:"+ list.size() +"/t暂时不能执行生产任务!");
				try {
					// 由于条件不满足，生产者线程阻塞
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 生产条件满足情况下，生产num的产品
			for(int i = 0; i < num; i++) {
				list.add(new Object());
			}
			
			System.out.println("【已经生产产品数】:"+ num +"/t【现仓储量为】:"+ list.size());
			
			list.notifyAll();
		}
	}
	
	/**
	 * 消费num个产品
	 * @param num
	 */
	public void consumer(int num) {
		synchronized(list) {
			while(list.size() - num < 0) {
				System.out.println("【要消费的产品数量】:"+ num +"/t【库存量】:"+ list.size() +"/t暂时不能执行消费任务!");
				try {
					// 由于条件不满足，消费者线程阻塞
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for(int i = 0; i < num; i++) {
				list.remove();
			}
			System.out.println("【已经消费产品数】:"+ num +"/t【现仓储量为】:"+ list.size());
			
			list.notifyAll();
		}
	}

}