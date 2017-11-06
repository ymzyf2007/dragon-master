package com.dragon.basic.数学计算;

/**
 * 求5!
 *
 */
public class 阶乘 {
	
	public static int jiecheng(int n) {
		if(n == 0) {
			return 1;
		} else {
			return n * jiecheng(n - 1);
		}
	}
	
	public static void main(String[] args) {
		// 用递归解决
		System.out.println(jiecheng(5));
		
//		int sum = 1;
//		for(int i = 1; i <= 5; i++) {
//			sum *= i;
//		}
//		System.out.println("sum=" + sum);
	}

}