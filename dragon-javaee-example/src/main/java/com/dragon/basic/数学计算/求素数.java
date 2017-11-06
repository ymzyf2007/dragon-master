package com.dragon.basic.数学计算;

/**
 * 输出101~200之间的素数
 * 
 * 素数即只能被1和其本身整除的数。
 *
 */
public class 求素数 {
	
	public static void main(String[] args) {
		int count = 0;
		for(int data = 101; data <= 200; data++) {
			boolean isPrime = true;
			for(int i = 2; i < Math.sqrt(data); i++) {
				if(data % i == 0) {
					isPrime = false;
					break;
				}
			}
			
			if(isPrime) {
				++count;
				System.out.println("素数=【" + data + "】");
			}
		}
		
		System.out.println("素数个数=【" + count + "】");
	}

}