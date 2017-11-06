package com.dragon.basic.数学计算;

/**
 * 求1的数目
 * 给定一个十进制正整数N，写下从1开始，到N的所有整数，然后数一下其中出现的所有“1”的个数
 * 例如：N=2，写下1，2。这样只出现了1个“1”
 *
 */
public class GetOneSum {
	
	/**
	 * 求正整数1的个数
	 * @param n
	 */
	public static int f(int n) {
		int count = 0;
		while(0 != n) {
			count += (n % 10 == 1) ? 1 : 0;
			n /= 10;
		}
		return count;
	}
	
	/**
	 * 计算从1到n的所有“1”的个数
	 * 缺点：效率低下
	 * @param n
	 * @return
	 */
	public static int getOneSum(int n) {
		int count = 0;
		for(int i = 1; i <= n; i++) {
			count += f(i);
		}
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println(getOneSum(10000));
	}

}