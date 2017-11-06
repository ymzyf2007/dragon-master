package com.dragon.algorithm.array;

/**
 * 两个有序数组合并为一个有序数组
 *
 */
public class ArrayMerge {
	
	public static int[] merge(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		int aIndex = 0;	// 标记a数组索引
		int bIndex = 0;	// 标记b数组索引
		int mIndex = 0;	// 合并数组索引
		while(aIndex < a.length && bIndex < b.length) {
			if(a[aIndex] <= b[bIndex]) {
				result[mIndex++] = a[aIndex++];
			} else {
				result[mIndex++] = b[bIndex++];
			}
		}
		
		// 继续添加没有添加完的元素
		while(aIndex < a.length) {
			result[mIndex++] = a[aIndex++];
		}
		while(bIndex < b.length) {
			result[mIndex++] = b[bIndex++];
		}
		
		return result;
	}
	
	/**
	 * 两个排好序的数组去掉重复数据合并
	 * @param a
	 * @param b
	 * @return
	 */
	public static int[] mergeDelDupliacte(int[] a, int[] b) {
		int[] data = new int[a.length + b.length];
		
		int aIndex = 0;
		int bIndex = 0;
		int mIndex = 0;
		while(aIndex < a.length && bIndex < b.length) {
			if(a[aIndex] < b[bIndex]) {
				data[mIndex++] = a[aIndex++];
			} else if(a[aIndex] == b[bIndex]) {
				data[mIndex++] = a[aIndex++];
				++bIndex;
			} else {
				data[mIndex++] = b[bIndex++];
			}
		}
		
		while(aIndex < a.length) {
			data[mIndex++] = a[aIndex++];
		}
		
		while(bIndex < b.length) {
			data[mIndex++] = b[bIndex++];
		}
	
		return data;
	}
	
	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 5, 6, 7 };
		int[] b = { 1, 2, 4, 5, 8, 9, 10, 11, 12, 13, 14 };
		
		int[] c = merge(a, b);
//		int[] c = mergeDelDupliacte(a, b);
		if(c != null && c.length > 0) {
			for(int i = 0; i < c.length; i++) {
				System.out.print(c[i] + "\t");
			}
		}
	}

}