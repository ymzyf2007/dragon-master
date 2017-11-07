package com.dragon.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符/字符串处理工具类
 *
 */
public class StringUtil {
	
	private StringUtil() {
	}
	
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
	
	/**
	 * 使用java.security.MessageDiagest加密规则对字符串进行加密，如果成功，返回加密后的字符串，否则返回原字符串
	 * @param password
	 * @param algorithm   “SHA”或者“MD5”
	 * @return
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return password;
		}
		md.update(unencodedPassword);    //使用指定的byte数组更新摘要
		//通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		
		return buf.toString();
	}
	
	/**
	 * 判断是否包含中文（该方法没有区分中文的标点符号）
	 * 
	 * @param args
	 */
	public static boolean isContainChinese(String str) {
		for(int i = 0; i < str.length(); i++) {
			if(str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+"))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 计算字符串长度,中文两个字节
	 * @param data
	 * @return
	 */
	public static int length(String data) {
		int count = 0;
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("[\u4e00-\u9fa5]+$");
		for(int i = 0; i < data.length(); i++) {
			java.util.regex.Matcher m = p.matcher("" + data.charAt(i));
			if(m.matches()) {
				count += 2;
				continue;
			}
			count++;
		}
		
		return count;
	}
	
	/**
	 * 指定某些字符转换为大写
	 */
	public static String toUpperChar(String data, int[] index) {
		if(data == null || "".equals(data)) {
			return data;
		}
		if(index == null || index.length <= 0) {
			return data;
		}
		StringBuffer sb = new StringBuffer(data);
		for(int i = 0; i < index.length; i++) {
			if(index[i] < 0 || index[i] > data.length() - 1) {
				continue;
			}
			sb.setCharAt(index[i], Character.toUpperCase(sb.charAt(index[i])));
		}
		
		return sb.toString();
	}
	
	/**
	 * 指定某个字符转换为小写
	 * @param data
	 * @param index
	 * @return
	 */
	public static String toLowerChar(String data, int index) {
		if(data == null || "".equals(data)) {
			return data;
		}
		if(index < 0 || index >= data.length()) {
			return data;
		}
		StringBuffer sb = new StringBuffer(data);
		sb.setCharAt(index, Character.toLowerCase(sb.charAt(index)));
		
		return sb.toString();
	}
	
	/**
	 * 指定某些字符转换为小写
	 * @param data
	 * @param index
	 * @return
	 */
	public static String toLowerChar(String data, int[] index) {
		if(data == null || "".equals(data)) {
			return data;
		}
		if(index == null || index.length <= 0) {
			return data;
		}
		StringBuffer sb = new StringBuffer(data);
		for(int i = 0; i < index.length; i++) {
			if(index[i] < 0 || index[i] > data.length() - 1) {
				continue;
			}
			sb.setCharAt(index[i], Character.toLowerCase(sb.charAt(index[i])));
		}
		
		return sb.toString();
	}
	
	/**
	 * 指定某个字符转换为大写
	 * @param data
	 * @param index
	 * @return
	 */
	public static String toUpperChar(String data, int index) {
		if(data == null || "".equals(data)) {
			return data;
		}
		if(index < 0 || index >= data.length()) {
			return data;
		}
		StringBuffer sb = new StringBuffer(data);
		sb.setCharAt(index, Character.toUpperCase(sb.charAt(index)));

		return sb.toString();
	}
	
	/**
	 * 根据已经编码的字符串返回是什么编码格式
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}
	
	public static void main(String[] args) {
//		String content = "db1#2012-12-20 11:44:03#135597144970415347909544|110#CF350057AE5F8DC1B83781D512DBA7794EA66DE9FD88D9BD703829DB6F5E18641D3EB352879C3D664F5DF65724B4A57D";
//		String content = "db1#2012-12-20 11:44:03#135597144970400|110#CF350057AE5F8DC1B83781D512DBA7794EA66DE9FD88D9BD703829DB6F5E18641D3EB352879C3D664F5DF65724B4A57D";
		
//		String content = "db1#2012-12-20 16:24:39#1355991537287|110#B2ECDA5D5EBA124A9564C6FAAE7E6D404EA66DE9FD88D9BD02E1231E04E2088D4A37B019AD1243941D25DA96A01C568F6B7356793855C3CC";
		
//		String content = "db1#2012-12-20 15:14:13#123456789012345|110#CF350057AE5F8DC1B83781D512DBA7794EA66DE9FD88D9BDD10D04AA436DE91C785EF2CA5FD324829023DC1B07B56F57B31F62D56FB6F56F";
		
		String content = "db1#2012-12-20 15:14:13#123456789012345|110#CF350057AE5F8DC1B83781D512DBA7794EA66DE9FD88D9BDD10D04AA436DE91C785EF2CA5FD324829023DC1B07B56F57B31F62D56FB6F56F";
		
		System.out.println(length(content));
	}

}