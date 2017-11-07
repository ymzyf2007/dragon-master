package com.dragon.basic.java.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 由表的字段名得到对应javabean的set方法
 */
public class FieldMappingSetterByReflect {
	
	public void setInsOffice(String str) {
		System.out.println("由表的字段名得到对应javabean的set方法:::" + str);
	}
	
	public void test() {
		//数据库字段名
		String columnName = "INS_OFFICE";
		StringBuffer sb = new StringBuffer();
		Class<? extends FieldMappingSetterByReflect> clzss = this.getClass();
		
		String[] fieldNames = columnName.toLowerCase().split("_");
		//将字段首字母大写
		for(String fieldName : fieldNames) {
			String firstWorld = fieldName.substring(0, 1).toUpperCase();
			sb.append(firstWorld);
			sb.append(fieldName.substring(1, fieldName.length()));
		}
		String methodName = "set" + sb.toString();
		try {
			Method[] methods = clzss.getDeclaredMethods();
			for(Method method : methods) {
				if(methodName.equals(method.getName())) {
					method.invoke(this, "yuminyuminyanyanyanyanyanyan");
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new FieldMappingSetterByReflect().test();
	}
	
}