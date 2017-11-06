package com.dragon.basic.字符串;

public class Equals总结 {
	
	public static void main(String[] args) {
		String s1 = "a" + "b" + 1;
		String s2 = "ab1";
		System.out.println(s1 == s2);	// true【有疑问，这个跟下面的有什么区别呢】
		
		String a = "a";
		String b = a + "b";
		String c = "ab";
		String d = new String(b);
		System.out.println("b.equals(c) = ["+ b.equals(c) +"];");	// true
		System.out.println(b == c);	// false【这个有疑问】	
		System.out.println(c == d); // false
		// intern()方法的返回值还是字符串"ab"，表面上看起来好像这个方法没什么用处。但实际上，它做了个小动作： 检查字符串池里是否存在"abc"这么一个字符串，如果存在，就返回池里的字符串；如果不存在，该方法会把"abc"添加到字符串池中，然后再返回它的引用。
		System.out.println(c == d.intern());	// true
		System.out.println(b.intern() == d.intern());	// true
	}

}