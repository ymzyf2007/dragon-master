package com.dragon.book.thinking.chapter19;

public class EnumClass {
	
	public static void main(String[] args) {
		for(Shrubbery s : Shrubbery.values()) {
			// ordinal() 返回枚举常量的序数（它在枚举声明中的位置，其中初始常量序数为零）。
			System.out.println(s + " ordinal: " + s.ordinal());
			// compareTo(E o) 比较此枚举与指定对象的顺序。在该对象小于、等于或大于指定对象时，分别返回负整数、零或正整数。 枚举常量只能与相同枚举类型的其他枚举常量进行比较。
			System.out.println(s.compareTo(Shrubbery.CRAWLING) + " ");
			// equals(Object other) 当指定对象等于此枚举常量时，返回 true。
			System.out.println(s.equals(Shrubbery.CRAWLING) + " ");
			System.out.println(s == Shrubbery.CRAWLING);
			// getDeclaringClass() 返回与此枚举常量的枚举类型相对应的 Class 对象。
			System.out.println(s.getDeclaringClass());
			// 返回此枚举常量的名称，在其枚举声明中对其进行声明。
			System.out.println(s.name());
			System.out.println("--------------------------------");
		}
		
		for(String s : "HANGING CRAWLING GROUND".split(" ")) {
			Shrubbery shrub = Enum.valueOf(Shrubbery.class, s);
			System.out.println(shrub);
		}
	}
	
	

}