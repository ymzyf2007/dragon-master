package com.dragon.book.thinking.chapter19.c7使用接口组织枚举;

/**
 * 使用接口组织枚举原因：
 * 无法从enum继承子类有时很令人沮丧。这种需求有时源自我们希望扩展enum中的元素，有时是因为我们希望使用子类将一个enum中的元素进行分组
 * 
 * 解决方法：
 * 在一个接口的内部，创建实现该接口的枚举，以此将元素进行分组
 * 
 * 总结：对于enum而言，实现接口事使其子类化的唯一办法。
 *
 */
public interface Food {
	
	enum Appetizer implements Food {
		SALAD, SOUP, SPRING_ROLLS
	}
	
	enum MainCourse implements Food {
		LASAGNE, BURRITO, PAD_THAI, LENTILS, HUMMOUS, VINDALOO
	}
	
	enum Dessert implements Food {
		TIRAMISU, GELATO, BLACK_FOREST_CAKE, FRUIT, CREME_CARAMEL
	}
	
	enum Coffee implements Food {
		BLACK_COFFEE, DECAF_COFFEE, ESPRESSO, LATTE, CAPPUCCINO, TEA, HERB_TEA
	}
	
}