package com.dragon.book.effective;

import java.util.HashSet;
import java.util.Set;

public class 第36条_坚持使用Override注解 {
	
	private final char first;
	private final char second;
	
	public 第36条_坚持使用Override注解(char first, char second) {
		this.first = first;
		this.second = second;
	}
	
	public boolean equals(第36条_坚持使用Override注解 b) {
		return b.first == first && b.second == second;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		super.equals(obj);
//		
//		if(!(obj instanceof 第36条_坚持使用Override注解))
//			return false;
//		第36条_坚持使用Override注解 b = (第36条_坚持使用Override注解) obj;
//		
//		return b.first == first && b.second == second;
//	}

	public int hashCode() {
		return 31 * first + second;
	}
	
	/**
	 * HashSet新增对象时，首先判断新增对象的hashCode是否跟集合中元素的hashCode是否相等，如果相等，则判断新增对象是否跟集合中元素equals，如果两者皆符合，则认为存在该元素，否则添加该对象
	 * 
	 * 1、如果两个对象equals，则两个对象的hashCode一定相等
	 * 2、如果两个对象的hashCode相等，则两个对象不一定equals
	 * 3、如果两个对象的hashCode不相等，则两个对象一定不相等
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Set<第36条_坚持使用Override注解> s = new HashSet<第36条_坚持使用Override注解>();
		for(int i = 0; i < 10; i++)
			for(char ch = 'a'; ch <= 'z'; ch++)
				s.add(new 第36条_坚持使用Override注解(ch, ch));
		
		System.out.println(s.size());
	}

}