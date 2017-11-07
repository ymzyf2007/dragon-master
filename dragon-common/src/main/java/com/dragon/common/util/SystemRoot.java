package com.dragon.common.util;

import java.io.UnsupportedEncodingException;

/**
 * 系统根路径
 *
 */
public class SystemRoot {

	private static String rootPath;
	
	private static String classPath;
	
	static {
		init();
	}
	
	private static void init() {
		classPath = System.class.getResource("/").toString();
		rootPath = System.getProperty("user.dir");
		classPath = classPath.substring(5);
		if(System.getProperty("os.name").startsWith("Window")) {
			classPath = classPath.substring(1);
		}
		try {
			rootPath = java.net.URLDecoder.decode(rootPath, "UTF-8");
			classPath = java.net.URLDecoder.decode(classPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static String getRootPath() {
		return rootPath;
	}

	public static String getClassPath() {
		return classPath;
	}
	
	public static String getRootPathAsWebApp() {
		StringBuffer sb = new StringBuffer(classPath);
		sb.delete(sb.lastIndexOf("WEB-INF"), sb.length());
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("classPath=【"+ classPath +"】");
		System.out.println("rootPath=【"+ rootPath +"】");
		System.out.println("getRootPathAsWebApp()=【"+ getRootPathAsWebApp() +"】");
	}
	
}