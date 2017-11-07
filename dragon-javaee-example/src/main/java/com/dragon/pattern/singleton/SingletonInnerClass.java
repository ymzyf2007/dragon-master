package com.dragon.pattern.singleton;

/**
 * 利用内部类实现单例
 * JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
 * 这样当我们第一次调用getInstance的时候，JVM能够帮我们保证instance只被创建一次，并且会保证把赋值给instance的内存初始化完毕
 *
 */
public class SingletonInnerClass {
	
	private SingletonInnerClass() {
	}
	
	private static class SingletonContainer {
		private static SingletonInnerClass instance = new SingletonInnerClass();
	}
	
	public static SingletonInnerClass getInstance() {
		return SingletonContainer.instance;
	}

}