package com.dragon.book.thinking.chapter19.c4values方法的神秘之处;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * 其实Enum类并没有values()方法。values()方法是由编译器添加的static方法。
 * 
 * 
 * 
 * HashSet与TreeSet区别:
 * 1、TreeSet 是二叉树实现的,Treeset中的数据是自动排好序的，不允许放入null值 
 * 2、HashSet 是哈希表实现的,HashSet中的数据是无序的，可以放入null，但只能放入一个null
 * 相同点：两者中的值都不能重复
 *
 */
public class Reflection {
	
	public static Set<String> analyze(Class<?> enumClass) {
		System.out.println("----- Analyzing " + enumClass + " -----");
		System.out.println("Interfaces:");
		for(Type t : enumClass.getGenericInterfaces()) {
			System.out.println(t);
		}
		System.out.println("Base: " + enumClass.getSuperclass());
		System.out.println("Methods: ");
		Set<String> methods = new TreeSet<String>();
		for(Method m : enumClass.getMethods()) {
			methods.add(m.getName());
		}
		System.out.println(methods);
		
		return methods;
	}
	
	public static void main(String[] args) {
		Set<String> exploreMethods = analyze(Explore.class);
		Set<String> enumMethods = analyze(Enum.class);
		
		System.out.println("Explore.containsAll(Enum)? " + exploreMethods.containsAll(enumMethods));
		System.out.println("Explore.removeAll(Enum): ");
		exploreMethods.removeAll(enumMethods);
		System.out.println(exploreMethods);
	}
	
//	----- Analyzing class org.alex.book.thinking.chapter19.c4values方法的神秘之处.Explore -----
//	Interfaces:
//	Base: class java.lang.Enum
//	Methods: 
//	[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, values, wait]
//	----- Analyzing class java.lang.Enum -----
//	Interfaces:
//	java.lang.Comparable<E>
//	interface java.io.Serializable
//	Base: class java.lang.Object
//	Methods: 
//	[compareTo, equals, getClass, getDeclaringClass, hashCode, name, notify, notifyAll, ordinal, toString, valueOf, wait]
//	Explore.containsAll(Enum)? true
//	Explore.removeAll(Enum): 
//
//	[values]

}