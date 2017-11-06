package com.dragon.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexUtil {
	
	private static final String MOBILE_REGEX = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[6,7,8])|(19[8,9])|(166))\\d{8}$";
	
	private RegexUtil() {
	}
	
	/** 
     * 匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 146/147/148+任意数
     * 198/199+任意数
     */  
    public static boolean isMobile(String str) throws PatternSyntaxException {  
        Pattern p = Pattern.compile(MOBILE_REGEX);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    public static void main(String[] args) {
    	System.out.println(isMobile("18475551885"));
    }
    
}