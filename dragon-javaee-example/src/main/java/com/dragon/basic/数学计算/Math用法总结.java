package com.dragon.basic.数学计算;

import java.math.BigDecimal;

/**
 * 位运算符包括:　与（&）、非（~）、或（|）、异或（^）
 *　&：当两边操作数的位同时为1时，结果为1，否则为0。如1100&1010=1000
 *　| ：当两边操作数的位有一边为1时，结果为1，否则为0。如1100|1010=1110
 *　~：0变1,1变0
 *　^：两边的位不同时，结果为1，否则为0.如1100^1010=0110
 *
 */
public class Math用法总结 {
	
	public static void main(String[] args) {
		double i = 2;
		double j = 2.1;
		double k = -2.5;
		double m = 2.9510;
		
		// Math.floor(double a)：向下取整
		System.out.println("向下取整：Math.floor("+ i +")=" + Math.floor(i));	// 2.0
		System.out.println("向下取整：Math.floor("+ j +")=" + Math.floor(j));	// 2.0
		System.out.println("向下取整：Math.floor("+ k +")=" + Math.floor(k));	// -3.0
		System.out.println("向下取整：Math.floor("+ m +")=" + Math.floor(m));	// 2.0
		
		// Math.ceil(double a)：向上取整
		System.out.println("向上取整：Math.ceil("+ i +")=" + Math.ceil(i));	// 2.0
		System.out.println("向上取整：Math.ceil("+ j +")=" + Math.ceil(j));	// 3.0
		System.out.println("向上取整：Math.ceil("+ k +")=" + Math.ceil(k));	// -2.0
		System.out.println("向上取整：Math.ceil("+ m +")=" + Math.ceil(m));	// 3.0
		
		// Math.round(double a)：四舍五入
		System.out.println("四舍五入：Math.round("+ i +")=" + Math.round(i));	// 2
		System.out.println("四舍五入：Math.round("+ j +")=" + Math.round(j));	// 2
		System.out.println("四舍五入：Math.round("+ k +")=" + Math.round(k));	// -2
		System.out.println("四舍五入：Math.round("+ m +")=" + Math.round(m));	// 3
		
		Math用法总结 t = new Math用法总结();
		System.out.println(t.getRound(102.2857812945));
		
		System.out.println(formatAmount(123522120));
		System.out.println(formatAmount(123));
		
		System.out.println("Math.round(-11.5)=" + Math.round(-11.8));
	}
	
	public double getRound(double d) {
		BigDecimal deSource = new BigDecimal(d);
		double iRound = deSource.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return iRound;
	}
	
	/**
	 * 保留小数点格式化
	 * @param amount
	 * @return
	 */
	public static String formatAmount(double amount) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		double fAmount = (double) amount / 100;
		return df.format(fAmount);
	}

}