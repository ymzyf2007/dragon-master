package com.dragon.rmq.common;

public class StringUtil {
	
	private StringUtil() {
	}
	
	public static boolean isEmpty(String s) {
		if("".equals(s.trim()))
			return true;
		
		return false;
	}
	
	public static boolean isNullOrEmpty(String s) {
		if(null == s)
			return true;
		if(isEmpty(s))
			return true;
		
		return false;
	}

}