package com.dragon.basic.字符串;

public class 求频率最高字 {
	
	public static void main(String[] args) {
		String s = "天工网定位于我的建设门户、我的工作社区，倾听用户的声音，关注用户的需求是天工生存与发展的根基，网上会员大都是通过口碑相传得知天工网，并成为天工网的忠实用户";
		
		int max = 0;
		char result = 'a';
		char c;
		if(s != null && !"".equals(s)) {
			for(int i = 0; i < s.length(); i++) {
				c = s.charAt(i);
				int count = 0;
				
				String tmp = s;
				while(tmp.indexOf(c) != -1) {
					tmp = tmp.substring(tmp.indexOf(c) + 1);
					++count;
				}
				
				if(max < count) {
					max = count;
					result = c;
				}
			}
			System.out.println("字符串中出现频率最高字=" + result + "；次数为=" + max);
		}
	}

}