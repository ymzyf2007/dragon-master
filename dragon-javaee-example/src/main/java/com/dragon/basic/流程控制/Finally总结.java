package com.dragon.basic.流程控制;

public class Finally总结 {
	
	public static int test() {
		int x = 1;
		try {
//			return x;
		} finally {
			++x;
		}
		
		return x;
	}
	
	public static int test2() {
		int x = 1;
		try {
			return x;
		} finally {
			System.out.println("有没有被执行?");   // 有执行,只是返回值已经返回去了
			++x;
			System.out.println("x=【"+ x +"】");	// x=【2】
		}
	}
	
	public static void main(String[] args) {
		int rt1 = test();
		System.out.println("rt1=【"+ rt1 +"】");	// rt1=【2】
		
		int rt2 = test2();
		System.out.println("rt2=【"+ rt2 +"】");	// rt2=【1】
	}

}
