package com.dragon.basic.字符串;

public class 分割支付 {
	
	public static void split() {
//		String aaa = " ##";
//		String[] bbb = aaa.split("#");
//		System.out.println(bbb.length); //输出1
//		System.out.println(bbb[0] + "=bbb[0]");  //" "
		
//		String aaa = "##";
//		String[] bbb = aaa.split("#");
//		System.out.println(bbb.length); //输出0
		
//		String aaa = "#2#";
//		String[] bbb = aaa.split("#");
//		System.out.println(bbb.length); //输出2
//		System.out.println(bbb[0] + "=bbb[0]");
//		System.out.println(bbb[1] + "=bbb[1]");  //2
		
		String aaa = "## ";
		String[] bbb = aaa.split("#");
		System.out.println(bbb.length); //输出3
		System.out.println(bbb[0] + "=bbb[0]"); //
		System.out.println(bbb[1] + "=bbb[1]");  //
		System.out.println(bbb[2] + "=bbb[2]");  //" "
	}
	
	public static void main(String[] args) {
		split();
	}

}