package com.dragon.basic.java.lang;

public class IntegerTest {
	
	public static void main(String[] args) {
		Integer i01 = 59;
		int i02 = 59;
		Integer i03 = Integer.valueOf(59);
		Integer i04 = new Integer(59);
		
		System.out.println(i01 == i02);	// true
//		System.out.println(i01 == i03);	// true
//		System.out.println(i03 == i04);	// false
//		System.out.println(i02 == i04);	// true
	}

}