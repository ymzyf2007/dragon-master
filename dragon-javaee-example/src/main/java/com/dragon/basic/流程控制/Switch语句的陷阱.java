package com.dragon.basic.流程控制;

public class Switch语句的陷阱 {
	
	public static void main(String[] args) {
		// i = 2时输出：2；i = 8时输出：
		/*
		 * default
		 * 0
		 * 1
		 * 2
		 */
		int i = 8;	
		switch(i) {
		default:
			System.out.println("default");
		case 0:
			System.out.println("0");
		case 1:
			System.out.println("1");
		case 2:
			System.out.println("2");
			break;
		case 3:
			System.out.println("3");
		case 4:
			System.out.println("4");
			break;
		}
		
		
		int x = 0;
        switch (x) {
        default:
            System.out.println("default");
        case 0:
            System.out.println(0);
        case 1:
            System.out.println(1);
        case 2:
            System.out.println(2);
        }
        // 输出：
        /*
		 * 0
		 * 1
		 * 2
		 */
        
//        int x = 0;
//        switch (x) {
//        case 0:
//            System.out.println(0);
//        case 1:
//            System.out.println(1);
//        case 2:
//            System.out.println(2);
//        default:
//            System.out.println("default");
//        }
        // 输出：
        /*
		 * 0
		 * 1
		 * 2
		 * default
		 */
        
        /**
         * 看了结果，可以这样理解：
         * （1）switch语句关键地方是进入点，有了进入点没有break的情况下会执行到底
         * （2）没有匹配值的时候default就是进入点，进入default以后会和普通进入点一样，如果没有break继续执行下面的语句
         * （3）如果有break则是从进入点到break中间的语句执行
         */
		
	}

}