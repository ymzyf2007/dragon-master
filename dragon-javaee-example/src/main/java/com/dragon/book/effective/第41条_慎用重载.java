package com.dragon.book.effective;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class 第41条_慎用重载 {
	
	public static String classify(Set<?> set) {
		return "Set";
	}
	
	public static String classify(List<?> list) {
		return "List";
	}
	
	public static String classify(Collection<?> c) {
//		return "UnKnow Collection";
		
		// 正解
		return c instanceof Set ? "Set" : c instanceof List ? "List" : "UnKnow Collection"; 
	}
	
	
	/**
	 * 输出三个都是：UnKnow Collection
	 * 为什么？因为调用哪个重载（overloading）方法是在编译时做出决定的。参数的编译时类型都是相同的，每次迭代的运行时类型都是不同的，
	 * 但这并不影响对重载方法的选择，因为该参数的编译类型都是Collection
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Collection<?>[] collections = { new HashSet<String>(), new ArrayList<BigInteger>(), new HashMap<String, String>().values() };
		for(Collection<?> c : collections)
			System.out.println(classify(c));
	}

}