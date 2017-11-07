package com.dragon.app.分页;

import java.util.ArrayList;
import java.util.List;

public class 前台分页测试 {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("i");
		list.add("j");
		
		list.add("k");
		list.add("l");
		list.add("m");
		list.add("n");
		
		PageModel<String> pageModel = new PageModel<String>(list, 5);
		List<String> subList = pageModel.getDataByPage(3);
		for(String s : subList) {
			System.out.println(s);
		}
		
		System.out.println();
		subList = pageModel.getPreviousPage();
		for(String s : subList) {
			System.out.println(s);
		}
	}

}