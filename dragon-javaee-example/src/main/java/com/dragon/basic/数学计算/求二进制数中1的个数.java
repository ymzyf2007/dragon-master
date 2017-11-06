package com.dragon.basic.数学计算;

/**
 * 求二进制数中1的个数，要求算法执行效率高
 *
 */
public class 求二进制数中1的个数 {
	
	/**
	 * 解决方案：
	 * 将要计算的数每次右移一位，然后再与00000001作与运算，如果是1，则最后一位为1
	 * 
	 */
	public static int f(int n) {
		int num = 0;
		while(0 != n) {
			num += n & 0x1;
			n >>= 1;
		}
		
		return num;
	}
	
	public static void main(String[] args) {
		System.out.println(f(1001));
	}

}