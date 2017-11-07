package com.dragon.basic.java.util.map;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
	
	/**
	 * LinkedHashMap保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的.也可以在构造时用带参数，按照应用次数排序。在遍历的时候会比HashMap慢，不过有种情况例外，当HashMap容量很大，实际数据较少时，遍历起来可能会比LinkedHashMap慢，因为LinkedHashMap的遍历速度只和实际数据有关，和容量无关，而HashMap的遍历速度和他的容量有关。
	 * TreeMap实现SortMap接口，能够把它保存的记录根据键排序,默认是按键值的升序排序，也可以指定排序的比较器，当用Iterator 遍历TreeMap时，得到的记录是排过序的。
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("1", "1");
		m.put(null, null);
		m.put(null, "8");
		m.put("1", "1");
		m.put("2", "2");
		m.put("3", "3");
		
		System.out.println("m.size() = " + m.size());
//		for(Iterator<String> it = m.keySet().iterator(); it.hasNext();) {
//			String key = it.next();
//			String value = m.get(key);
//			System.out.println("key = " + key + "; value = " + value);
//		}
		
		// Map四种遍历
		// 第一种
//		for(String key : m.keySet()) {
//			System.out.println("key = " + key + "; value = " + m.get(key));
//		}
		
		// 第二种
//		Iterator<Map.Entry<String, String>> it = m.entrySet().iterator();
//		while(it.hasNext()) {
//			Map.Entry<String, String> entry = it.next();
//			System.out.println("key = " + entry.getKey() + "; value = " + entry.getValue());
//		}
		
		// 第三种：推荐，尤其是容量大时
		for(Map.Entry<String, String> entry : m.entrySet()) {
			System.out.println("key = " + entry.getKey() + "; value = " + entry.getValue());
		}
		
		// 第四种：只遍历value，但不遍历key
		for(String v : m.values()) {
			System.out.println("value = " + v);
		}
	}

}