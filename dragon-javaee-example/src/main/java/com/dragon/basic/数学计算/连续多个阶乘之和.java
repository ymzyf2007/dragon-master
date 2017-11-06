package com.dragon.basic.数学计算;

/**
 * 求1+2!+3!+...+20!的和
 *
 */
public class 连续多个阶乘之和 {
	
	public static void main(String[] args) {
		int sum = 0;
		int tmp = 1;
		for(int i = 1; i <= 5; i++) {
			tmp = tmp * i;
			sum += tmp;
		}
		
		System.out.println("sum=" + sum);
	}
	
//	1*1
//	1*2
//	1*2*3
//	1*2*3*4
//	1*2*3*4*5
	
//	1
//	2
//	6
//	24
//	120
	
//	153
}