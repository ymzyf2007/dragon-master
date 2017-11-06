package com.dragon.basic.拷贝.浅拷贝;

/**
 * B包含A的引用
 * 
 */
public class B implements Cloneable {

	private String b_attr1;
	private String b_attr2;
	private A a;

	public String getB_attr1() {
		return b_attr1;
	}

	public void setB_attr1(String b_attr1) {
		this.b_attr1 = b_attr1;
	}

	public String getB_attr2() {
		return b_attr2;
	}

	public void setB_attr2(String b_attr2) {
		this.b_attr2 = b_attr2;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}