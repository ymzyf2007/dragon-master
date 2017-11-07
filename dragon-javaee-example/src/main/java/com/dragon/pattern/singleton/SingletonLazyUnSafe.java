package com.dragon.pattern.singleton;

/**
 * 懒汉模式，多线程环境下，线程不安全
 *
 */
public class SingletonLazyUnSafe {
	
	private static SingletonLazyUnSafe instance = null;
	
	private SingletonLazyUnSafe() {
	}
	
	public static SingletonLazyUnSafe getInstance() {
		if(instance == null) {
			instance = new SingletonLazyUnSafe();
		}
		return instance;
	}

}