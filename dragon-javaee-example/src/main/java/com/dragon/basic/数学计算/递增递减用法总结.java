package com.dragon.basic.数学计算;

public class 递增递减用法总结 {
	
	public static void main(String[] args) {
		int a = 222;
		a += 2;
		
		System.out.println(a);	// 224
		System.out.println(a + (--a) + (a++));	// 224 + 223 + 223 = 670  a = 224
		System.out.println(a);	// 224
	}
	
}
