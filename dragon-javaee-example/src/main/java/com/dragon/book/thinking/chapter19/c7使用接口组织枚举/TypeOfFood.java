package com.dragon.book.thinking.chapter19.c7使用接口组织枚举;

import com.dragon.book.thinking.chapter19.c7使用接口组织枚举.Food.Appetizer;
import com.dragon.book.thinking.chapter19.c7使用接口组织枚举.Food.Coffee;
import com.dragon.book.thinking.chapter19.c7使用接口组织枚举.Food.Dessert;
import com.dragon.book.thinking.chapter19.c7使用接口组织枚举.Food.MainCourse;

public class TypeOfFood {
	
	public static void main(String[] args) {
		Food food = Appetizer.SALAD;
		food = MainCourse.LASAGNE;
		food = Dessert.GELATO;
		food = Coffee.CAPPUCCINO;
		System.out.println(food);
	}

}