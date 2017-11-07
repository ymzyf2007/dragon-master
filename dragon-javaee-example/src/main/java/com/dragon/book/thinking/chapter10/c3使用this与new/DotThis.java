package com.dragon.book.thinking.chapter10.c3使用this与new;

public class DotThis {
	
	void f() {
		System.out.println("DotThis.f()");
	}

	public class Inner {
		/**
		 * 如果内部类需要生成对外部类对象的引用，可以使用外部类的名字后面紧跟圆点和this。
		 * 但是为什么需要生成对外部类的引用？因为非静态内部类本身就可以访问外部类的所有成员和方法。难道这个是给静态内部类使用？
		 * @return
		 */
		public DotThis outer() {
			return DotThis.this;
			// A plain "this" would be Inner's "this"
		}
	}

	public Inner inner() {
		return new Inner();
	}

	public static void main(String[] args) {
		DotThis dt = new DotThis();
		DotThis.Inner dti = dt.inner();
		dti.outer().f();
	}
	
}