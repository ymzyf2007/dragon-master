package com.dragon.basic.java.util.list;

import java.io.Serializable;
import java.util.List;

/**
 * 接口继承接口
 *
 */
public interface UcList<E> extends Serializable {
	
	boolean add(E e);
	
	String toString();
	
	java.util.Iterator<E> iterator();
	
	List<E> list();

}