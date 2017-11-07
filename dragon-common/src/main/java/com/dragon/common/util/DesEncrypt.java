package com.dragon.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncrypt {
	/**
	 * 密钥
	 */
	public static final String KEY = "music!@#$";
	
	public static final String DES = "DES";
	
	/**
	 * 加密
	 * @param src：明文（字节）
	 * @param key：密钥
	 * @return 密文（字节）
	 * @throws Exception 
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		//DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		//从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		//创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		//Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		//用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		//正式执行加密操作
		return cipher.doFinal(src);
	}
	
	/**
	 * 解密
	 * @param src：密文（字节）
	 * @param key：密钥
	 * @return：明文（字节）
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		//DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		//从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		//创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		
		return cipher.doFinal(src);
	}
	
	/**
	 * 加密
	 * @param src：明文（字节）
	 * @return 密文（字节）
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src) throws Exception {
		return encrypt(src, KEY.getBytes());
	}
	
	/**
	 * 解密
	 * @param src：密文（字节）
	 * @return 明文（字节）
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src) throws Exception {
		return decrypt(src, KEY.getBytes());
	}
	
	/**
	 * 加密
	 * @param src：明文（字符串）
	 * @return 密文（16进制字符串）
	 */
	public final static String encrypt(String src) {
		try {
			return byte2hex(encrypt(src.getBytes(), KEY.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param src：密文（字符串）
	 * @return 明文（字符串）
	 */
	public final static String decrypt(String src) {
		try {
			return new String(decrypt(hex2byte(src.getBytes()), KEY.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加密
	 * @param src：明文（字节）
	 * @return 密文（16进制字符串）
	 */
	public static String encryptToString(byte[] src) {
		return encrypt(new String(src));
	}
	
	/**
	 * 解密
	 * @param src：密文（字节）
	 * @return 明文（字符串）
	 */
	public static String decryptToString(byte[] src) {
		return decrypt(new String(src));
	}
	
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for(int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if(stmp.length() == 1)
				hs = hs + "0" + stmp;
			else 
				hs = hs + stmp;
		}
		
		return hs.toUpperCase();
	}
	
	public static byte[] hex2byte(byte[] b) {
		if((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for(int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	public static void main(String[] args) {
		String src = "111111111111EAST!@#$10101028";
		String crypto = DesEncrypt.encrypt(src);
		System.out.println("密文:" + crypto);
		System.out.println("解密后:"+ DesEncrypt.decrypt(crypto));
	}

}