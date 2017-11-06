package com.dragon.basic.数学计算;

import java.util.Scanner;

/**
 * 求s=a+aa+aaa+aaaa+aa...a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)，几个数相加有键盘控制
 *
 */
public class 求N个a的值 {
	
	public static void main(String[] args) {
		int a = 0;
		int n = 0;
		Scanner mScanner = new Scanner(System.in);
		System.out.print("输入数字a的值： ");
		a = mScanner.nextInt();
		System.out.print("输入相加的项数：");
		n = mScanner.nextInt();
		
		int sum = 0;
		int tmp = 0;
		
		int i = 0;
		while(i < n) {
			tmp = a + tmp;
			sum = sum + tmp;
			a = a * 10;
			
			++i;
		}
		
		System.out.println("sum=" + sum);
	}

}