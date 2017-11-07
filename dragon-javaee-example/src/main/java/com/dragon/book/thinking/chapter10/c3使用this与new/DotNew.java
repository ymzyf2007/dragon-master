package com.dragon.book.thinking.chapter10.c3使用this与new;

public class DotNew {
	
	public Inner getInner() {
		return new Inner();
	}
	
	public class Inner {
		
		public void say() {
			System.out.println("say()");
		}
		
	}

	public static void main(String[] args) {
		DotNew dn = new DotNew();
		DotNew.Inner dni = /*dn.getInner()*/dn.new Inner();
		dni.say();
	}
	
}
