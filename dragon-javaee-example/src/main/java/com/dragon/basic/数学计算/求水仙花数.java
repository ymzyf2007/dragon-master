package com.dragon.basic.数学计算;

/**
 * 题目：打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。
 * 例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
 *
 */
public class 求水仙花数 {
	
	public static void main(String[] args) {
		int b1 = 0;	// 个位数
		int b2 = 0;	// 十位数
		int b3 = 0;	// 百位数
		
		for(int data = 100; data < 1000; data++) {
			b1 = data % 100 % 10;
			b2 = data % 100 / 10;
			b3 = data / 100;
			
			if(data == ((b1 * b1 * b1) + (b2 * b2 * b2) + (b3 * b3 * b3))) {
				System.out.println("水仙花数=【" + data + "】");
			}
		}
	}

}