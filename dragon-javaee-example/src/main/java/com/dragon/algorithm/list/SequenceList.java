package com.dragon.algorithm.list;

import java.util.Arrays;

/**
 * JAVA数组实现顺序表
 * 1、引入了JAVA泛型类，因此定义了一个Object[] 类型的数组，从而可以保存各种不同类型的对象。
 * 2、默认构造方法创建了一个默认大小为16的Object数组；带参数的构造方法创建一个指定长度的Object数组
 * 3、实现的顺序表的基本操作有：返回表的长度、获取指定索引处的元素（注意是索引，而不是位置。索引以下标0开始，位置以下标1开始）、按值查找数据元素的位置、直接插入元素（顺序表尾部）、向指定位置插入元素、直接删除元素（在顺序表尾部）、删除指定索引处元素、判断表是否为空、清空表。
 * 4、在Java类库中，java.util.ArrayList 类实现了顺序表，因此可以直接使用JAVA类库中的ArrayList来完成顺序表的各种操作。
 *
 * @param <T>
 */
public class SequenceList<T> {

	private static final int DEFAULT_SIZE = 16;	// 默认初始值
	
	private Object[] elementData;	// 该数组用来保存顺序表中的元素
	private int capacity;	// 保存数组的长度
	private int size;	// 保存顺序表中当前元素的个数
	
	/**
	 * 以默认的大小创建顺序表
	 */
	public SequenceList() {
		capacity = DEFAULT_SIZE;
		elementData = new Object[capacity];
	}
	
	/**
	 * 以指定的大小创建顺序表
	 * @param initSize
	 */
	public SequenceList(int initSize) {
		if(initSize < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initSize);
		capacity = 1;
		while(capacity < initSize)
			capacity = capacity << 1;	// 将capacity设置成大于initSize的最小2次方
		elementData = new Object[capacity];
	}
	
	/**
	 * 在顺序表的末尾添加一个元素
	 * @param element
	 */
	public void add(T element) {
		insert(element, size);
	}
	
	/**
	 * 在顺序表的指定索引处插入一个元素
	 * @param element
	 * @param index
	 */
	public void insert(T element, int index) {
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException("顺序表索引越界");
		ensureCapacity(size + 1);	// 确保顺序表满时进行扩容，从而能插入元素
		// 将指定索引后的所有元素向后移动一个位置
		for(int i = size; i > index; i--)
			elementData[i] = elementData[i - 1];
		elementData[index] = element;
		size++;	// 顺序表中的元素个数增1
	}
	
	private void ensureCapacity(int minCapacity) {
        // 当数组容量已满时，对数组进行扩容。将容量扩展到大于minCapacity的最小2的次方
		if(minCapacity > capacity) {
            while(capacity < minCapacity)
                capacity <<= 1;
            elementData = Arrays.copyOf(elementData, capacity);
        }
    }
	
	/**
	 * 获取顺序表中索引为index的元素，index表示索引，从0开始
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("顺序表索引越界");
		return (T) elementData[index];
	}
	
	/**
	 * 删除顺序表中的最后一个元素
	 * @return
	 */
	public T remove() {
		return delete(size - 1);
	}
	
	/**
	 * 删除顺序表中指定索引处的元素
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T delete(int index) {
		if(index < 0 || index > size - 1)
			throw new IndexOutOfBoundsException("顺序表索引越界");
		T oldValue = (T) elementData[index];
		// 计算需要移动的元素个数
		int numMoved = size - 1 - index;
		if(numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;	// 让垃圾回收器及时回收，避免内存泄露
		return oldValue;
	}
	
	/**
	 * 查看顺序表中指定元素的索引，若未找到，返回-1
	 * @param element
	 * @return
	 */
    public int locate(T element) {
    	for(int i = 0; i < size; i++)
            if(elementData[i].equals(element))
                return i;
        return -1;
    }
	
	/**
	 * 判断顺序表是否为空表
	 * @return
	 */
    public boolean empty() {
    	return size == 0;
    }
    
    /**
     * 清空顺序表
     */
    public void clear() {
        Arrays.fill(elementData, null);	// 将数组elementData中的每个元素都赋值null
        size = 0;
    }
	
  	/**
  	 * 获取顺序表中当前元素的个数
  	 * @return
  	 */
    public int length() {
    	return size;
    }
    
    public String toString() {
    	if(size == 0)
    		return "[]";
    	else {
            StringBuilder sb = new StringBuilder("[");
            for(int i = 0; i < size; i++)
                sb.append(elementData[i].toString() + ", ");
            int len = sb.length();
            // 删除由于上面for循环中最后添加的多余的两个字符 (一个是逗号，一个是空格符号)
            return sb.delete(len - 2, len).append("]").toString();
        }
    }
}