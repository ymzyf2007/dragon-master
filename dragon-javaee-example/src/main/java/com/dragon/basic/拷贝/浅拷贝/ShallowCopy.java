package com.dragon.basic.拷贝.浅拷贝;

/**
 * 浅拷贝
 * 浅拷贝是指拷贝对象时仅仅拷贝对象本身（包括对象中的基本变量），而不拷贝该对象包含的引用指向的对象。
 * 例如：该实例，对象B中包含对象A的引用，浅拷贝B得到B1，B1中依然包含对A的引用。
 *
 */
public class ShallowCopy {
	
	public static void main(String[] args) {
		A a = new A();
		a.setA_attr1("a_attr1");
		a.setA_attr2("a_attr2");
		B b = new B();
		b.setB_attr1("b_attr1");
		b.setB_attr2("b_attr2");
		b.setA(a);
		
		try {
			B b1 = (B) b.clone();
			b1.setB_attr1("b1_attr1");
			b1.setB_attr2("b1_attr2");
			b1.getA().setA_attr1("a1_attr1");
			b1.getA().setA_attr2("a1_attr2");
			
			System.out.println("a.getA_attr1()=" + a.getA_attr1() + "; a.getA_attr2()=" + a.getA_attr2() + "; b.getB_attr1()=" + b.getB_attr1() + "; b.getB_attr2()=" + b.getB_attr2());
			System.out.println("b1.getA().getA_attr1()=" + b1.getA().getA_attr1() + "; b1.getA().getA_attr2()=" + b1.getA().getA_attr2() + "; b1.getB_attr1()=" + b1.getB_attr1() + "; b1.getB_attr2()=" + b1.getB_attr2());
			
			// 输出
			/*a.getA_attr1()=a1_attr1; a.getA_attr2()=a1_attr2; b.getB_attr1()=b_attr1; b.getB_attr2()=b_attr2
			b1.getA().getA_attr1()=a1_attr1; b1.getA().getA_attr2()=a1_attr2; b1.getB_attr1()=b1_attr1; b1.getB_attr2()=b1_attr2*/
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
}