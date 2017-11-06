package com.dragon.basic.gc;

/**
 * 栈溢出
 * 栈溢出也比较常见，有时我们编写的递归调用没有正确的终止条件时，就会使方法不断递归，栈的深度不断增大，最终发生栈溢出。
 * 
 */
public class StackOverflow {
	private static int stackDepth = 1;	// 栈的深度
	public static void stackOverflow() {
		stackDepth++;
		stackOverflow();
	}
	public static void main(String[] args) {
		try {
			stackOverflow();
		} catch(Exception e) {
			System.err.println("Stack depth: " + stackDepth);
			e.printStackTrace();
		}
	}
}