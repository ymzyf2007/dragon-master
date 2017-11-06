package com.dragon.basic.初始化;

class A {
	A(String s) {
		System.out.println(s);
	}
	
	A() {
		System.out.println("A默认构造函数被调用");
	}
}

class T {
	// 普通成员初始化
	A a1 = new A("a1成员初始化");
	// 静态成员初始化
	static A a = new A("静态成员a初始化");
	// 静态块初始化
	static {
		System.out.println("static块执行");
		if(a == null)
			System.out.println("a is null");
		a = new A("静态块内初始化a成员变量");
	}
	
	T() {
		System.out.println("T默认构造函数被调用");
	}
}

public class Java初始化顺序 {
	
	public static void main(String[] args) {
//		new T();
		
		// 输出结果为：
		/*
		 *  静态成员a初始化
		 *  static块执行
		 *  静态块内初始化a成员变量
		 *  a1成员初始化
		 *  T默认构造函数被调用
		 *  
		 */
		
		/**
		 * 由此可以得出结论：
		 * 1、在无继承情况下的Java初始化顺序为：
		 * a、静态成员变量首先初始化（注意，static块可以看做一个静态成员，其执行顺序和其在类中的申明的顺序有关）
		 * b、普通成员初始化
		 * c、执行构造方法
		 * 
		 * 静态成员及static块 --> 普通成员初始化 --> 构造方法调用
		 */
		
		new T2();
		
		// 输出结果为：
		/*
		 *  父类static块1执行
		 *  父类静态成员staticA1初始化
		 *  父类静态成员staticA2初始化
		 *  父类static块2执行
		 *  子类静态成员subStaticA1初始化
		 *  子类静态成员subStaticA2初始化
		 *  子类static块执行
		 *  
		 *  父类a1成员初始化
		 *  父类a2成员初始化
		 *  父类T1默认构造函数初始化
		 *  子类a1成员初始化
		 *  子类a2成员初始化
		 *  子类T2默认构造函数初始化
		 *  
		 */
		
		/**
		 * 由此可以得出结论：
		 * 2、在Java继承情况下的初始化顺序为：
		 * a、继承体系的所有静态成员初始化（先父类，后之类）
		 * b、父类初始化完成（普通成员的初始化-->构造方法的调用）
		 * c、子类初始化（普通成员-->构造方法）
		 * 
		 * 父类静态成员及static块 --> 子类静态成员及static块 --> 父类普通成员初始化 --> 父类构造方法调用 --> 子类普通成员初始化 --> 子类构造方法调用
		 */
	}
	
}

class T1 {
	static {
		System.out.println("父类static块1执行");
	}
	static A staticA1 = new A("父类静态成员staticA1初始化");
	A a1 = new A("父类a1成员初始化");
	static A staticA2 = new A("父类静态成员staticA2初始化");
	static {
		System.out.println("父类static块2执行");
	}
	
	T1() {
		System.out.println("父类T1默认构造函数初始化");
	}
	
	A a2 = new A("父类a2成员初始化");
}

class T2 extends T1 {
	static A subStaticA1 = new A("子类静态成员subStaticA1初始化");
	
	T2() {
		System.out.println("子类T2默认构造函数初始化");
	}
	
	A a1 = new A("子类a1成员初始化");
	
	static A subStaticA2 = new A("子类静态成员subStaticA2初始化");
	
	static {
		System.out.println("子类static块执行");
	}
	
	A a2 = new A("子类a2成员初始化");
}