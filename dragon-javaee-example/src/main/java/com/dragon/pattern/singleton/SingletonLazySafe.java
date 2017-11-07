package com.dragon.pattern.singleton;

/**
 * 懒汉模式，线程安全
 * 但是它却很影响性能：每次调用getInstance方法的时候都是必须获得对象的锁，而实际上，当单例实例被创建以后，其后的请求没有必要再使用互斥机制了。
 *
 */
public class SingletonLazySafe {
	
	private static SingletonLazySafe instance = null;
	
	private SingletonLazySafe() {
	}
	
	public static synchronized SingletonLazySafe getInstance() {
		if(instance == null) {
			instance = new SingletonLazySafe();
		}
		return instance;
	}

}