package com.dragon.basic;

public class 环境变量 {
	
	public static void main(String[] args) {
		System.out.println("Java运行时环境版本:" + System.getProperty("java.version"));
		System.out.println("Java运行时环境供应商:" + System.getProperty("java.vendor"));
		System.out.println("Java供应商的URL:" + System.getProperty("java.vendor.url"));
		System.out.println("Java安装目录:" + System.getProperty("java.home"));
		System.out.println("Java虚拟机规范版本:" + System.getProperty("java.vm.specification.version"));
		System.out.println("Java类格式版本号:" + System.getProperty("java.class.version"));
		System.out.println("Java类路径:" + System.getProperty("java.class.path"));
		System.out.println("操作系统的名称:" + System.getProperty("os.name"));
		System.out.println("操作系统的架构:" + System.getProperty("os.arch"));
		System.out.println("操作系统的版本:" + System.getProperty("os.version"));
		System.out.println("用户的主目录:" + System.getProperty("user.home"));
		System.out.println("用户的当前工作目录:" + System.getProperty("user.dir"));
		System.out.println("自定义变量:" + System.getenv("JAVA_HOME"));
	}

}