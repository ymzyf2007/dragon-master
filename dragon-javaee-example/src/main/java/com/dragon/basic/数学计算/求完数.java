package com.dragon.basic.数学计算;

/**
 * 一个数如果恰好等于它的因子之和，这个数就称为 "完数 "
 *
 */
public class 求完数 {
	
	public static void main(String[] args) {
		
		for(int i = 1; i < 1000; i++) {
			int t = 0;
			for(int j = 1; j <= i/2; j++) {
				if(i % j == 0) {
					t = t + j;
				}
			}
			
			if(i == t) {
				System.out.println("完数为：" + t);
			}
		}
		
	}

}