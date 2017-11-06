package com.dragon.basic.逻辑计算;

public class 条件运算符 {
	
	private static void testCal(int data) {
		char c = data >= 90 ? 'A' : data >= 60 ? 'B' : 'C';
		System.out.println(c);
	}
	
	public static void main(String[] args) {
		testCal(61);
		testCal(98);
		testCal(56);
	}

}