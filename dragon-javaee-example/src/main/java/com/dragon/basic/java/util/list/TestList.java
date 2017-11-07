package com.dragon.basic.java.util.list;

import java.util.LinkedList;
import java.util.List;

public class TestList {
	
	/**
	 * List特点：元素有放入顺序，元素可重复
	 * ArrayList 采用的是数组形式来保存对象的，这种方式将对象放在连续的位置中，所以最大的缺点就是插入删除时非常麻烦
	 * LinkedList 采用的将对象存放在独立的空间中，而且在每个空间中还保存下一个链接的索引， 但是缺点就是查找非常麻烦 要从第一个索引开始
	 * @param args
	 */
	public static void main(String[] args) {
//		List<String> l1 = new ArrayList<String>();
		List<String> l1 = new LinkedList<String>();
		l1.add(null);
		l1.add("1");
		l1.add("2");
		l1.add("2");
		l1.add(null);
		l1.add("3");
//		for(int i = 0; i < l1.size(); i++) {
//			String v = l1.get(i);
//			System.out.println("v=" + v);
//		}
		
		for(String v : l1) {
			System.out.println("v=" + v);
		}
	}

}