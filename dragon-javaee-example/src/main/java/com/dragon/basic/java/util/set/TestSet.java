package com.dragon.basic.java.util.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestSet {
	
	/**
	 * HashSet实现Set接口，由哈希表支持。它不保证set的迭代顺序：特别是它不保证该顺序恒久不变。
	 * 此类允许用null元素。HashSet中不允许有重复元素，这是因为HashSet是根据HashMap实现的，
	 * HashSet中的元素都存放在HashMap的key上面，而value中的值都是统一的一个private static final Object PRESENT = new Object();
	 * HashSet跟HashMap一样，都是一个存放链表的数组。
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> s1 = new HashSet<String>();
		s1.add(null);
		s1.add("1");
		s1.add("2");
		s1.add("2");
		s1.add(null);
		s1.add("3");
		
		for(Iterator<String> it = s1.iterator(); it.hasNext();) {
			String v = it.next();
			System.out.println("v=" + v);
		}
		
//		for(String v : s1) {
//			System.out.println("v=" + v);
//		}
	}

}