package com.dragon.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.WeakHashMap;

public class DateUtil {
	
	private static WeakHashMap<String, SimpleDateFormat> simpleDateFormats = new WeakHashMap<String, SimpleDateFormat>();
	
	private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static String DEFAULT_LONGFORMAT = "yyyyMMddHHmmss";
	
	private DateUtil() {
	}
	
	private static synchronized SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat sdf = simpleDateFormats.get(format);
		if(sdf == null) {
			sdf = new SimpleDateFormat(format);
			simpleDateFormats.put(format, sdf);
		}
		return sdf;
	}
	
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		synchronized (sdf) {
			return sdf.format(date);
		}
	}
	
	public static String format(Date date) {
		return format(date, DEFAULT_FORMAT);
	}
	
	public static Date parse(String date, String format) {
		SimpleDateFormat sdf = getSimpleDateFormat(format);
		try {
			synchronized (sdf) {
				return sdf.parse(date);
			}
		} catch (ParseException e) {
			throw new RuntimeException("Parse date " + date + " with format " + format + " failed.", e);
		}
	}
	
	public static Date parse(String date) {
		return parse(date, DEFAULT_FORMAT);
	}
	
	public static int formatToInt(Date date, String format) {
		return Integer.parseInt(format(date, format));
	}
	
	public static long formatToLong(Date date, String format) {
		return Long.parseLong(format(date, format));
	}
	
	public static long formatToLong(Date date) {
		return Long.parseLong(format(date, DEFAULT_LONGFORMAT));
	}
	
	public static Date parseFromInt(int date, String format) {
		return parse(String.valueOf(date), format);
	}
	
	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}
	
	/**
	 * 日期相加
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(c.getTimeInMillis() + day*24*3600*1000);
		return c.getTime();
	}

}