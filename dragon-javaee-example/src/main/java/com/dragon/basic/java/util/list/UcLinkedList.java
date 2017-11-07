package com.dragon.basic.java.util.list;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 固定大小链表  1. 当达到最大值时，删除最先加入的元素，再添加
 *
 */
public class UcLinkedList<E> implements UcList<E> {

	private static final long serialVersionUID = 4706095705776470380L;

	//默认链表只有10个元素
	private int maxSize = 10;
	private LinkedList<E> list = new LinkedList<E>();
	
	public UcLinkedList() {
	}
	
	public UcLinkedList(int maxSize) {
		this.maxSize = maxSize;
	}
	
	public UcLinkedList(List<? extends E> list, int maxSize) {
		this.maxSize = maxSize;
		if(list.size() > maxSize)
			throw new Error("maxSize is smaller than list's size.");
		this.list.addAll(list);
	}
	
	private void check() {
		if(this.list.size() > (maxSize - 1))
			this.list.removeLast();
	}
	
	public boolean add(E e) {
		check();
		this.list.addFirst(e);
		return true;
	}
	
	public String toString() {
		return this.list.toString();
	}

	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	public List<E> list() {
		return this.list;
	}
	
	public static void main(String[] args) {
		UcLinkedList<Integer> ucList = new UcLinkedList<Integer>();
		for(int i = 0; i < 15; i++) {
			ucList.add(i);
		}
		System.out.println(ucList);
		ucList.add(8);
		ucList.add(11);
		System.out.println(ucList);
	}

}