package com.dragon.basic.java.io.file;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileTest {
	
	//BufferedReader:缓冲字符输入流
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("E:\\s1.txt"));
			String line = null;
			int i = 1;
			while(null != (line = reader.readLine())) {
				System.out.println("第"+ i +"行:【"+ line +"】");
				i++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}