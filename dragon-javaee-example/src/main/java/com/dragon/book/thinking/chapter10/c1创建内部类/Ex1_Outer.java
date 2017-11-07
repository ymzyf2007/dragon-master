package com.dragon.book.thinking.chapter10.c1创建内部类;

public class Ex1_Outer {
	
	class Inner {
		Inner() {
			System.out.println("Inner()");
		}
	}
	
	Ex1_Outer() {
		System.out.println("Ex1_Outer()");
	}
	
	public Inner makeInner() {
		return new Inner();
	}
	
	public static void main(String[] args) {
		Ex1_Outer o = new Ex1_Outer();
		Inner i = o.makeInner();
	}

}