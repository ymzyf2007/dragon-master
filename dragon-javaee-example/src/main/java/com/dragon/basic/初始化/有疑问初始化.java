package com.dragon.basic.初始化;

class SuperClass {
	static {
		System.out.println("superclass init");
	}
	public static int value = 123;
}

class SubClass extends SuperClass {
	static {
		System.out.println("subclass init");
	}
}

public class 有疑问初始化 {
	public static void main(String[] args) {
		System.out.println(SubClass.value);
		/*
		 * 输出结果
		 *  superclass init
		 *  123
		 *  
		 *  由结果可以看出只输出了“superclass init”，没有输出“subclass init”。
		 *  这是因为对于静态字段，只有直接定义该字段的类才会被初始化，因此当我们通过子类来引用父类中定义的静态字段时，只会触发父类的初始化，而不会触发子类的初始化。
		 */
		
		SubClass[] sca = new SubClass[10];
		/*
		 * 输出结果为空
		 * 没有输出“superclass init”说明没有触发类SuperClass的初始化阶段
		 */
		System.out.println(sca.length);
	}
}