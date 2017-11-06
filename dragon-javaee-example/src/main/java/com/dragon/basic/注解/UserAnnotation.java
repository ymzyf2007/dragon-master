package com.dragon.basic.注解;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target：这个注解来指定给哪一类java成员注解，指定注解目标该是什么样的东西
 * @Retention：表示注解运行的状态，换句话说，注解改在什么样的状态下才能运行
 *
 */
@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAnnotation {
	
	/**
	 * 首先，方法必须是public的，去掉public，默认也是public,接口interface不也是这样么
	 * 其次，default默认值不是必须的，方法必须有返回值，返回值可以是java中复杂对象，也可以是基本类型，枚举都行
	 * 如 ElementType[] value();
	 */
	public int id() default 0;
	
	public String name() default "";
	
	public int age() default 18;
	
	public String gender() default "M";

}