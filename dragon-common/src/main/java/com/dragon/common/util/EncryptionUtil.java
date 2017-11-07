package com.dragon.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
	
	/**
	 * 使用加密规则对字符串进行加密，如果加密成功，返回加密后的字符串，否则返回原字符串
	 * @param s
	 * @param algorithm
	 * @return
	 */
	public static String encodeStr(String s, String algorithm) {
		byte[] unencodeBytes = s.getBytes();
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return s;
		}
		
		md.reset();
		md.update(unencodeBytes);
		
		byte[] encodeBytes = md.digest();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < encodeBytes.length; i++) {
			if((encodeBytes[i] & 0xff) < 0x10) {
				sb.append("0");
			}
			sb.append(Long.toString(encodeBytes[i] & 0xff, 16));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("字符串123456经过SHA加密后：" + EncryptionUtil.encodeStr("123456", "SHA"));
	}
	
}