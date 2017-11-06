package com.dragon.basic;

class A {
	void f() {
		System.out.println("A.f()");
	}
}

class B extends A {
	// overload(重载)
	void f(int i) {
		System.out.println("B.f()");
	}
}

class C extends B {
	// override(覆盖)
	void f() {
		System.out.println("C.f()");
	}
}

/**
 * overload: 重载; override: 重写(覆盖)
 *
 */
public class OverloadAndoverride {

	public static void main(String[] args) {
		A a = new A();
		a.f();	// A.f()
		B b = new B();
		b.f();	// A.f()
		C c = new C();
		c.f();	// C.f()
		
		A d = new C();	// 多态，父类的引用指向子类的对象
		d.f();	// C.f()
	}
	
}