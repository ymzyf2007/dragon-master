package com.dragon.basic.gc;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存泄漏测试
 * 
 * @author YM
 * 
 */
public class MemoryLeak {

	// 声明缓存对象
	@SuppressWarnings("rawtypes")
	private static final Map map = new HashMap();

	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		try {
			Thread.sleep(10000);	// 给打开visualvm时间
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 循环添加对象到缓存
		for (int i = 0; i < 1000000; i++) {
			TestMemory t = new TestMemory();
			map.put("key" + i, t);
		}
		System.out.println("first");
		// 为dump出堆提供时间
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 1000000; i++) {
			TestMemory t = new TestMemory();
			map.put("key" + i, t);
		}
		System.out.println("second");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 3000000; i++) {
			TestMemory t = new TestMemory();
			map.put("key" + i, t);
		}
		System.out.println("third");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 4000000; i++) {
			TestMemory t = new TestMemory();
			map.put("key" + i, t);
		}
		System.out.println("forth");
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("qqqq");
	}

}

class TestMemory {
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}