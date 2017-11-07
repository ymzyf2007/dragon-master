package com.dragon.basic.java.util.concurrent.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	
	private Lock lock = new ReentrantLock();
	
	public /*synchronized*/ void add() {
		try {
			lock.lock();
			System.out.println("add Service");
			delete();
		} finally {
			lock.unlock();
		}
	}
	
	public /*synchronized*/ void delete() {
		try {
			lock.lock();
			System.out.println("delete Service");
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		new ReentrantLockTest().add();
	}

}