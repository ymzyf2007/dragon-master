package com.dragon.basic.数学计算;

public class 求分解因子 {
	
	private static void fenjie(int data) {
		System.out.print("data=【");
		int n = 2;
		while(n <= data) {
			if(n == data) {
				System.out.println(n + "】");
				break;
			}
			
			if(data % n == 0) {
				System.out.print(n + "*");
				data = data / n;
			} else {
				++n;
			}
		}
	}
	
	public static void main(String[] args) {
		fenjie(90);
	}

}