package com.dragon.book.thinking.chapter19;

/**
 * 注意：
 * 我们可以向enum中添加方法。enum甚至可以有main方法。
 * 如果打算在枚举类型定义自己的方法，那么必须在enum实例序列的最后添加一个分号。
 * 同时，Java要求你必须先定义enum实例。如果在定义enum实例之前定义了任何方法或属性，那么在编译时就会得到错误信息。
 *
 */
public enum OzWitch {
	
	// instances must be defined first.before methods:
	WEST("Miss Gulch, aka the Wicked Witch of the West"),
	NORTH("Glinda, the Good Witch of the North"),
	EAST("Wicked Witch of the East, wearer of the Ruby Slippers, crushed by Dorothy's house"),
	SOUTH("Good by inference, but missing");
	
	private String description;
	// Constructor must be package or private access
	private OzWitch(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static void main(String[] args) {
		for(OzWitch witch : OzWitch.values())
			System.out.println(witch);
//			System.out.println(witch + ": " + witch.getDescription());
	}

}