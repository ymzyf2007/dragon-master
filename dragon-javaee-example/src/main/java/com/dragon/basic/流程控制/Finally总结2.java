package com.dragon.basic.流程控制;

public class Finally总结2 {
	public static String output = "";

	public static void foo(int i) {
		try {
			if (i == 1) {
				throw new Exception();
			}
		} catch (Exception e) {
			output += "2";
			return;	// 注意执行finally后会返回这里return回去
		} finally {
			output += "3";
		}
		output += "4";
	}

	public static void main(String[] args) {
		foo(0);
		foo(1);
		System.out.println(output);	// 3423
	}
	
}