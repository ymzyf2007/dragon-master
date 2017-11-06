package com.dragon.basic.gc;

/**
 * 堆内存溢出测试
 * 堆溢出比较简单，只需通过创建一个大数组对象来申请一块比较大的内存，就可以使堆发生溢出
 *
 */
public class HeapOverflow {
	
	private static final int MB = 1024 * 1024;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] bigMemory = new byte[1024 * MB];
		System.out.println("test heap overflow.");
	}

}