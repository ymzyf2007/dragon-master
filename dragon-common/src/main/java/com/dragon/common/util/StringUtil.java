package com.dragon.common.util;

/**
 * 字符/字符串处理工具类
 *
 */
public class StringUtil {
	
	/**
	 * 判断字符串是否是null
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		if(null == s)
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否是null和空字符串
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s) {
		if(isNull(s))
			return true;
		if("".equals(s.trim()))
			return true;
		return false;
	}

}