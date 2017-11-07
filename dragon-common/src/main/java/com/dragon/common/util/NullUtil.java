package com.dragon.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 空值处理工具类
 *
 */
@SuppressWarnings("rawtypes")
public class NullUtil {
	
	/**
	 * MAP集合中键或值为空
	 */
	public static int MAP_ALL = 0;
	
	/**
	 * MAP集合中键为空
	 */
	public static int MAP_KEY = 1;
	
	/**
	 * MAP集合中值为空
	 */
	public static int MAP_VALUE = 2;

	private NullUtil() {
	}
	
	/**
	 * 去除数组中的空值
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object[] wipe(Object[] objs) {
		List list =  new ArrayList();
		for(int i = 0; i < objs.length; i++) {
			if(objs[i] != null) {
				list.add(objs[i]);
			}
		}
		
		return list.toArray();
	}
	
	/**
	 * 去除集合中的空对象
	 * @param c
	 */
	public static void wipe(Collection c) {
		if(c == null || c.size() <= 0) {
			return;
		}
		for(java.util.Iterator iterator = c.iterator(); iterator.hasNext();) {
			Object o = iterator.next();
			if(o == null) {
				c.remove(o);
			}
		}
	}
	
	/**
	 * 判断字符串是否为空，包括null
	 * @param s
	 * @return
	 */
	public static boolean isNullString(String s) {
		if(s == null || "".equals(s)) 
			return true;
		
		return false;
	}
	
	/**
	 * 过滤Map集合中的空值
	 * @param map
	 * @param filterValue
	 */
	@SuppressWarnings("unchecked")
	public static void wipe(Map map, int filterValue) {
		if(map == null || map.size() <= 0)
			return;
		if(filterValue < 0 || filterValue > 2)
			return;
		/*Hashtable不允许有空键值存在*/
		if(map instanceof java.util.Hashtable)
			return;
		/*遍历Map中keySet和entrySet的区别，貌似entrySet的效率高*/
		/*entrySet返回此映射中包含的映射关系的Set视图，对set中的更改和在映射中反映出来，
		反之亦然。支持iterator.remove、set.remove、removeAll，但是不支持add、addAll*/
		
		java.util.Set s = new java.util.HashSet(map.entrySet());
		java.util.Iterator iterator = s.iterator();
		switch(filterValue) {
			/*过滤所有主键和值为空的对象*/
			case 0:
				while(iterator.hasNext()) {
					Map.Entry entry = (Entry) iterator.next();
					if(entry.getKey() == null || entry.getValue() == null) {
						map.remove(entry.getKey());
					}
				}
				break;
			/*过滤所有主键为空的对象*/
			case 1:
				while(iterator.hasNext()) {
					Map.Entry entry = (Entry) iterator.next();
					if(entry.getKey() == null) {
						map.remove(entry.getKey());
					}
				}
				break;
			/*过滤所有值为空的对象*/
			case 2:
				while(iterator.hasNext()) {
					Map.Entry entry = (Entry) iterator.next();
					if(entry.getValue() == null) {
						map.remove(entry.getKey());
					}
				}
				break;
			default:
				break;
		}
	}
	
}