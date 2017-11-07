package com.dragon.basic.java.lang.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Eg1_GetMethods {
	
	public Eg1_GetMethods(String s) {
		System.out.println("s=" + s);
	}
	
	public static void main(String[] args) {
		// 1、Class对象
		Class<?> clazz = Eg1_GetMethods.class;	// 编译期获取Class对象
//		Class<?> clazz = null;
//		try {
//			clazz = Class.forName("test.java.lang.reflect.Eg1_GetMethods");	// 运行期获取Class对象
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		// 【疑问1：这两种方式有什么不一样？】
		
		// 2、类名
		System.out.println(clazz.getName());	// 获取全限定名
		System.out.println(clazz.getSimpleName()); // 获取类名
		
		// 3、修饰符（可以通过Class对象来访问一个类的修饰符，即public,private,static等等的关键字，你可以使用如下方法来获取类的修饰符）
		int modifiers = clazz.getModifiers();
		System.out.println("modifiers=" + modifiers);
		System.out.println("Modifier.isPublic(clazz.getModifiers())=" + Modifier.isPublic(clazz.getModifiers()));
		
		// 4、包信息（可以使用Class对象通过如下的方式获取包信息）
		Package pkg = clazz.getPackage();
		System.out.println("pkgName=" + pkg.getName());
		
		// 5、父类（通过Class对象你可以访问类的父类）
		Class<?> superClass = clazz.getSuperclass();
		System.out.println("superClass=" + superClass.getName());
		
		// 6、实现的接口
		// 注意：getInterfaces()方法仅仅只返回当前类所实现的接口。当前类的父类如果实现了接口，这些接口是不会在返回的Class集合中的，尽管实际上当前类其实已经实现了父类接口。
		Class<?>[] interfaces = clazz.getInterfaces();	// 由于一个类可以实现多个接口，因此getInterfaces();方法返回一个Class数组，在Java中接口同样有对应的Class对象。
		for(Class<?> c : interfaces) {
			System.out.println("c.getName()=" + c.getName());
		}
		
		// 7、构造器（可以访问一个类的构造器）
		// 返回的Constructor数组包含每一个声明为公有的（Public）构造方法
		Constructor<?>[] constructors = clazz.getConstructors();
		for(Constructor<?> constructor : constructors) {
			System.out.println("constructor.getName()=" + constructor.getName());
		}
		try {
			Constructor<?> c = clazz.getConstructor(new Class[] { String.class });
			System.out.println("c.getName()=" + c.getName());
			
			// 7.1、构造方法参数信息
			Class<?>[] parameterTypes = c.getParameterTypes();
			for(Class<?> parameterType : parameterTypes) {
				System.out.println("parameterType=" + parameterType.getName());
			}
			
			// 7.2、利用Constructor对象实例化一个类
			try {
				// constructor.newInstance()方法的方法参数是一个可变参数列表，但是当你调用构造方法的时候你必须提供精确的参数，即形参与实参必须一一对应。
				Eg1_GetMethods eg1 = (Eg1_GetMethods) c.newInstance("constructor-arg1");
				System.out.println("eg1.toString=" + eg1.toString());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		// 8、方法
		// 返回的Method对象数组包含了指定类中声明为公有的(public)的所有方法名集合。
		Method[] methods = clazz.getMethods();
		for(Method method : methods) {
			System.out.println("method=" + method.getName());
		}
		try {
			Method method = clazz.getMethod("setField1", new Class[] { int.class });
			// 如果你想要获取的方法没有参数，那么在调用getMethod()方法时第二个参数传入null即可
//			Method method = clazz.getMethod("getField1", null);
			// 8.1、方法参数信息以及返回类型
			// 方法参数信息
			Class<?>[] parameterTypes = method.getParameterTypes();
			for(Class<?> c : parameterTypes) {
				System.out.println("方法参数信息=" + c.getName());
			}
			// 返回类型
			Class<?> returnType = method.getReturnType();
			System.out.println("returnType.getName()=" + returnType.getName());
			
			// 8.2、通过Method对象调用方法
			try {
//				Object returnValue = method.invoke(new Eg1_GetMethods("new Obj()"), null);
//				System.out.println("returnValue=" + returnValue);
				
				Eg1_GetMethods obj = new Eg1_GetMethods("new Obj()");
				method.invoke(obj, 888);
				System.out.println("obj.getField1()=" + obj.getField1());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		
		// 9、变量
		// 返回的Field对象数组包含了指定类中声明为公有的(public)的所有变量集合。
		Field[] fields = clazz.getFields();
		for(Field field : fields) {
			// 9.1、变量名称
			System.out.println("fieldName=" + field.getName());
			// 9.2、变量类型
			System.out.println("fieldType=" + field.getType());
			// 9.3、获取或设置（get/set）变量值
			Eg1_GetMethods obj = new Eg1_GetMethods("abc");
			try {
				System.out.println("field.getInt(obj)=" + field.getInt(obj));
				field.set(obj, 10);	// 设置值
				System.out.println("field.getInt(obj)=" + field.getInt(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public int field1 = 0x00000010;

	public int getField1() {
		return field1;
	}

	public void setField1(int field1) {
		this.field1 = field1;
	}
	
}