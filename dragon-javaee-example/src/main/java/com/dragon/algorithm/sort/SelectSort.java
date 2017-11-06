package com.dragon.algorithm.sort;

/**
 * 选择排序法
 * 算法步骤：
 * 找到第一小的数字，放在第一个位置；
 * 再找到第二个小的数字，放在第二个位置。一次找一个数字，如此下去就会把所有数值按照顺序排好
 *
 */
public class SelectSort {
	
	public static void selectSort(int[] a) {
		for(int i = 0; i < a.length; i++) {
			int index = i;	// 查找第i小的数字
			for(int j = i + 1; j < a.length; j++) {
				if(a[j] < a[index]) {
					index = j;
				}
			}
			
			if(i != index) {
				a[i] ^= a[index];
				a[index] ^= a[i];
				a[i] ^= a[index];
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = { 7, 2, 8, 5, 3, 6, 1, 10, 11, 11, 9, 4, 12 };
		selectSort(a);
		
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

}