package com.dragon.basic;

/**
 * 1、若主方法的public改为private：编译不报错，运行的时候提示"Main method not public."
 * 2、去掉static：编译不报错，运行的时候提示"java.lang.NoSuchMethodError: main"
 * 3、如果将public static换成static public：编译不报错，运行正常
 * 4、如果将String[] args去掉：编译不报错，运行的时候提示"java.lang.NoSuchMethodError:main"
 *
 */
public class Main方法总结 {
	
	public static void main(String[] args) {
		System.out.println(args.length);
	}

}