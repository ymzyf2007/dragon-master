package com.dragon.algorithm.sort;

/**
 * 插入排序是一种最简单直观的排序算法，它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 算法步骤：
 * 1）将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列。
 * 2）从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置。（如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面。）
 *
 */
public class InsertSort {
	
	public static void insertSort(int[] a) {
		// 将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列，所以从第二个元素开始依次跟前面元素对比，找到自己的位置
		for(int i = 1; i < a.length; i++) {
			int key = a[i];	// 待排序
			for(int j = i - 1; j >= 0; j--) {
				if(a[j] > key) {
					a[j] ^= a[j + 1];
					a[j + 1] ^= a[j];
					a[j] ^= a[j + 1];
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = { 7, 2, 8, 5, 3, 6, 1, 10, 11, 11, 9, 4, 12 };
		insertSort(a);
		
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

}