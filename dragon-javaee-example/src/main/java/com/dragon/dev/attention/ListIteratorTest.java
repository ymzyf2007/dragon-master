package com.dragon.dev.attention;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 切勿用普通for循环遍历LinkedList
 *
 */
public class ListIteratorTest {

	private final static int LIST_SIZE = 100000;

	public static void main(String[] args) {
		List<Integer> arrayList = new ArrayList<Integer>();
		List<Integer> linkedList = new LinkedList<Integer>();

		for (int i = 0; i < LIST_SIZE; i++) {
			arrayList.add(i);
			linkedList.add(i);
		}

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < arrayList.size(); i++) {
			arrayList.get(i);
		}
		System.out.println("ArrayList遍历速度：" + (System.currentTimeMillis() - startTime) + "ms");

		startTime = System.currentTimeMillis();
		for (int i = 0; i < linkedList.size(); i++) {
			linkedList.get(i);
		}
		System.out.println("LinkedList遍历速度：" + (System.currentTimeMillis() - startTime) + "ms");
	}

}