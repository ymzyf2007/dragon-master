package com.dragon.basic.注解;

import java.lang.reflect.Field;

public class TestMain {
	
	@UserAnnotation(age = 20, gender = "F", id = 2014, name = "zhangsan")
	public Object obj;
	
	public static void main(String[] args) throws Exception {
		Field objField = TestMain.class.getField("obj");
		UserAnnotation ua = objField.getAnnotation(UserAnnotation.class);	// 得到注解,起到了标记的作用
		System.out.println(ua.age()+","+ua.gender()+","+ua.id()+","+ua.name());
	}

}