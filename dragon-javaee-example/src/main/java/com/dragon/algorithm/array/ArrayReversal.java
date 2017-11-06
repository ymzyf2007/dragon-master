package com.dragon.algorithm.array;

/**
 * 数组反转
 *
 */
public class ArrayReversal {
	
	public static void arrayReversal(int[] a) {
		for(int i = 0; i < a.length / 2; i++) {
			a[i] ^= a[a.length - i - 1];
			a[a.length - i - 1] ^= a[i];
			a[i] ^= a[a.length - i - 1];
		}
	}
	
	public static void main(String[] args) {
		int[] a = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		arrayReversal(a);
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

}