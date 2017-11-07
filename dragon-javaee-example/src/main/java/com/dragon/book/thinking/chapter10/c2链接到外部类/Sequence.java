package com.dragon.book.thinking.chapter10.c2链接到外部类;

interface Selector {
	boolean end();

	Object current();

	void next();
}

public class Sequence {
	private Object[] items;
	private int next = 0;

	public Sequence(int size) {
		items = new Object[size];
	}

	public void add(Object x) {
		if (next < items.length)
			items[next++] = x;
	}
	
	/**
	 * 非静态内部类
	 * 1.非静态内部类中不能定义静态变量
	 * 2.非静态内部类可以访问外部类的静态成员和非静态成员（包含方法）
	 * 
	 * 内部类是如何做到能访问外部类所有成员的访问权的呢？
	 * 实际上是当外部类创建内部类对象时，此内部类对象必定会秘密地捕获一个指向那个外部类对象的引用，
	 * 然后你访问外部类的成员时，就是用那个引用来选择外部类的成员。
	 */
	private class SequenceSelector implements Selector {
		private int i = 0;

		public boolean end() {
			return i == items.length;
		}

		public Object current() {
			return items[i];
		}

		public void next() {
			if (i < items.length)
				i++;
		}
	}

	public Selector selector() {
		return new SequenceSelector();
	}

	public static void main(String[] args) {
		Sequence sequence = new Sequence(10);
		for (int i = 0; i < 10; i++)
			sequence.add(Integer.toString(i));
		Selector selector = sequence.selector();
		while (!selector.end()) {
			System.out.print(selector.current() + " ");
			selector.next();
		}
	}
}