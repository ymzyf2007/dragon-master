package com.dragon.basic.字符串;

public class 统计字符 {
	
	public static void main(String[] args) {
		String s = "ac20!e34fa k f89";
		
		int digital = 0;
		int character = 0;
		int blank = 0;
		int other = 0;
		
//		for(int i = 0; i < s.length(); i++) {
//			char c = s.charAt(i);
//			if(c >= '0' && c <= '9') {
//				++digital;
//			} else if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
//				++character;
//			} else if(c == ' ') {
//				++blank;
//			} else {
//				++other;
//			}
//		}
		
		char[] chars = s.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if(c >= '0' && c <= '9') {
				++digital;
			} else if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
				++character;
			} else if(c == ' ') {
				++blank;
			} else {
				++other;
			}
		}
		
		System.out.println("字符串长度: " + s.length());
		System.out.println("数字个数: " + digital);
	    System.out.println("英文字母个数: " + character);
	    System.out.println("空格个数: " + blank);
	    System.out.println("其他字符个数:" + other );
	}

}