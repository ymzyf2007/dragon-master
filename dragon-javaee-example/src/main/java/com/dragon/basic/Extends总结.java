package com.dragon.basic;

public class Extends总结 {
	
	public static void main(String[] args) {
		Person p = new Boy();
		Boy b = (Boy) p;
		b.eat();
		
		/*#############转换错误：java.lang.ClassCastException####################*/
		// 父类不能强制转成子类
//		Boy b = (Boy) new Person();
//		b.eat();
	}

}

class Person {
	public void eat() {
		System.out.println("The people were eating...");
	}
}

class Boy extends Person {
	public void eat() {
		System.out.println("The boy were eating...");
	}
}

class Girl extends Person {
	public void eat() {
		System.out.println("The girl were eating...");
	}
}