package com.dragon.basic.java.lang.classloader;

/**
 * Class.forName和ClassLoader的区别
 *
 */
public class ClassLoaderTest {
	
	static {
		System.out.println("静态初始化...");
	}
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		try {
			System.out.println("before loadClass...");
			Class c = ClassLoaderTest.class.getClassLoader().loadClass("test.java.lang.classloader.ClassLoaderTest");
//			System.out.println("after loadClass...");
//			System.out.println("before newInstance...");
//			ClassLoaderTest t = (ClassLoaderTest) c.newInstance();
			
//			Class c = Class.forName("test.java.lang.classloader.ClassLoaderTest");
			
			System.out.println("after newInstance...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}