package com.dragon.book.thinking.chapter10.c1创建内部类;

public class Parcel1 {
	
	/**
	 * 内部类
	 *
	 */
	class Contents {
		private int i = 11;
		public int value() {
			return i;
		}
	}
	
	/**
	 * 内部类
	 *
	 */
	class Destination {
		private String label;
		Destination(String whereTo) {
			label = whereTo;
		}
		
		public String readLabel() {
			return label;
		}
	}
	
	public void ship(String dest) {
		Contents c = new Contents();
		Destination d = new Destination(dest);
		System.out.println(d.readLabel());
	}
	
	public static void main(String[] args) {
		Parcel1 p = new Parcel1();
		p.ship("Tasmania");
	}

}