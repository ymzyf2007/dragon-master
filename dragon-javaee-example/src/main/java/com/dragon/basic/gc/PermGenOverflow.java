package com.dragon.basic.gc;

/**
 * 永久代内存溢出
 * 永久代溢出可以新建很多ClassLoader来装载同一个类文件。因为方法区是保存类的相关信息的，所以当我们加载过多的类时就会导致方法区溢出。
 * CGLIB同样会缓存代理类的Class对象，但是我们可以通过配置让它不缓存Class对象，这样就可以通过反复创建代理类达到使方法区溢出的目的。
 * 
 */
public class PermGenOverflow {

	static class OOMObject {
	}

	public static void main(String[] args) {
		while (true) {
//			Enhancer enhancer = new Enhancer();
//			enhancer.setSuperclass(OOMObject.class);
//			enhancer.setUseCache(false);
//			enhancer.setCallback(new MethodInterceptor() {
//				@Override
//				public Object intercept(Object obj, Method method,
//						Object[] args, MethodProxy proxy) throws Throwable {
//					return method.invoke(obj, args);
//				}
//			});
//			OOMObject proxy = (OOMObject) enhancer.create();
//			System.out.println(proxy.getClass());
		}
	}

}