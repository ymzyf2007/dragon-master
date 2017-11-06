package com.dragon.basic.初始化;

/**
 * 分析:
1:SingleTon singleTon = SingleTon.getInstance();调用了类的SingleTon调用了类的静态方法，触发类的初始化 
2:类加载的时候在准备过程中为类的静态变量分配内存并初始化默认值 singleton=null count1=0,count2=0 
3:类初始化化，为类的静态变量赋值和执行静态代码快。singleton赋值为new SingleTon()调用类的构造方法 
4:调用类的构造方法后count=1;count2=1 
5:继续为count1与count2赋值,此时count1没有赋值操作,所有count1为1,但是count2执行赋值操作就变为0
 *
 */
class SingleTon {
	private static SingleTon singleTon = new SingleTon();
	public static int count1;
	public static int count2 = 0;
//	private static SingleTon singleTon = new SingleTon();	// 这个结果都是1
	
	private SingleTon() {
		count1++;
		count2++;
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}

public class 类初始化顺序 {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		SingleTon singleTon = SingleTon.getInstance();
		System.out.println("count1=" + singleTon.count1);	// 1
		System.out.println("count2=" + singleTon.count2);	// 0
	}
	
}