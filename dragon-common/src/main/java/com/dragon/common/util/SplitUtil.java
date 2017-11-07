package com.dragon.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 字符串截取组装工具类
 *
 */
@SuppressWarnings("rawtypes")
public class SplitUtil {
	
	private static java.util.StringTokenizer sk;
	
	/**
	 * 默认分割符
	 */
	private static String dor = "|";
	
	private SplitUtil() {
	}
	
	/**
	 * 根据默认分隔符来将字符串拆分成int数组
	 * @param data
	 * @return
	 */
	public static int[] toSplitInt(String data) {
		return toSplitInt(data, dor);
	}
	
	/**
	 * 根据默认分隔符来将字符串拆分成int数组
	 * @param data
	 * @param dor
	 * @return
	 */
	public static int[] toSplitInt(String data, String dor) {
		if(data == null || "".equals(data)) {
			return null;
		}
		if(data.indexOf(dor) == -1) {
			int[] d = new int[1];
			try {
				d[0] = Integer.parseInt(data);
			} catch(Exception e) {
				return null;
			}
			return d;
		}
		//按照指定字符串将源字符串标记
		sk = new java.util.StringTokenizer(data, dor);
		int[] d = new int[sk.countTokens()];
		int i = 0;
		while(sk.hasMoreTokens()) {
			String tmp = sk.nextToken();
			d[i] = Integer.parseInt(tmp);
			i++;
		}
		
		return d;
	}
	
	/**
	 * 根据默认分隔符来将字符串拆分成String数组
	 * @param data
	 * @return
	 */
	public static String[] toSplitString(String data) {
		return toSplitString(data, dor);
	}
	
	/**
	 * 根据默认分隔符来将字符串拆分成String数组
	 * @param data
	 * @param dor
	 * @return
	 */
	public static String[] toSplitString(String data, String dor) {
		if(data == null || "".equals(data)) {
			return null;
		}
		if(data.indexOf(dor) == -1) {
			String[] d = new String[]{data};
			return d;
		}
		sk = new java.util.StringTokenizer(data, dor);
		String[] d = new String[sk.countTokens()];
		int i = 0;
		while(sk.hasMoreTokens()) {
			d[i] = sk.nextToken();
			i++;
		}
		
		return d;
	}
	
	/**
	 * 根据默认的分割符将字符串分割成单值集合
	 * @param data
	 * @return
	 */
	public static Collection toSplitCollection(String data) {
		return toSplitCollection(data, dor);
	}
	
	/**
	 * 根据默认的分割符将字符串分割成单值集合
	 * @param data
	 * @param dor
	 * @return
	 */
	public static Collection toSplitCollection(String data, String dor) {
		if(data == null || "".equals(data)) {
			return null;
		}
		if(data.indexOf(dor) == -1) {
			List<String> list = new ArrayList<String>();
			list.add(data);
			return list;
		} 
		sk = new java.util.StringTokenizer(data, dor);
		List<String> list = new ArrayList<String>();
		while(sk.hasMoreTokens()) {
			list.add(sk.nextToken());
		}
		
		return list;
	}
	
	/**
	 * 根据默认的分隔符将单值集合组装成字符串
	 * @param cl
	 * @return
	 */
	public static String toString(Collection cl) {
		if(cl == null || cl.size() <= 0) {
			return "";
		}
		return toString(cl, dor);
	}
	
	/**
	 * 根据默认的分隔符将单值集合组装成字符串
	 * @param cl
	 * @param dor
	 * @return
	 */
	public static String toString(Collection cl, String dor) {
		if(cl == null || cl.size() <= 0) {
			return "";
		}
		java.util.Iterator iterator = cl.iterator();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while(iterator.hasNext()) {
			sb.append(iterator.next().toString());
			if(i != cl.size() - 1) {
				sb.append(dor);
			}
			i++;
		}
		
		return sb.toString();
	}
	
	/**
	 * 使用默认的分隔符将数组组装成字符串
	 * @param o
	 * @return
	 */
	public static String toString(Object[] o) {
		if(o == null || o.length <= 0) {
			return "";
		}
		return toString(o, dor);
	}
	
	/**
	 * 使用默认的分隔符将数组组装成字符串
	 * @param o
	 * @param dor
	 * @return
	 */
	public static String toString(Object[] o, String dor) {
		if(o == null || o.length <= 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < o.length; i++) {
			sb.append(o[i].toString());
			if(i != o.length - 1) {
				sb.append(dor);
			}
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String s = "1ww22|ss2|3sd|sdg|5g|g|g1g|g0";
		String[] a = toSplitString(s);
		System.out.println("数组长度：" + a.length);
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
	
}