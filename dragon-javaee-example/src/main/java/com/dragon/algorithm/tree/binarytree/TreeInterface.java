package com.dragon.algorithm.tree.binarytree;

/**
 * 树节点接口
 *
 * @param <T>
 */
public interface TreeInterface<T> {
	
	T getRootData();
	
	int getHeight();
	
	int getNumberOfNodes();
	
	boolean isEmpty();
	
	void clear();

}