package com.dragon.zset.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.dragon.rmq.common.RedisUtil;

/**
 * 问题描述：让您做一个电商平台，您如何设置一个在买家下订单后的”第60秒“发短信通知卖家发货，您需要考虑的是 像淘宝一样的大并发量的订单。
 * 
 * 第二种思路需要利用Redis的有序集合，说到使用 Redis 就不得不考虑Score的设计，因为它直接决定你代码的复杂度，你思路的清晰性，
 * 这里是通过精确到秒的时间做Score（去掉毫秒），然后使用线程每一秒扫一次，使用当前时间作为zrangeBysocre命令的Score去查询。
 * 详细请看代码
 *
 */
public class OrderHandler {
	
	public static final String KEY = "orders";
	private static AtomicLong currentOrderId = new AtomicLong(0);

	private static SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void initData() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, 60);	// 取当前时间的后60秒
			
			// 一天500万的成交量，8小时计算，每秒成交173订单
			for(int i = 0; i < 170; i++) {
				currentOrderId.getAndIncrement();
				RedisUtil.zadd(KEY, (double) removeMillis(cal), currentOrderId.toString());
				System.out.println("当前订单ID:--------" + currentOrderId);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public static void consumer() throws ParseException {
		Calendar cal = Calendar.getInstance();
		long score = removeMillis(cal);
		Set<String> orders = RedisUtil.zrangeByScore(KEY, 0, score);	// 早于当前时间的都应该被发送，因为可能订单太多没有处理过来
		if(orders == null || orders.size() == 0) {
			System.out.println("========暂时没有订单，时间：" + cal.getTime());
		}
		
		for(String order : orders) {
			System.out.println("^^^^^^^^处理订单，订单ID：" + order);
			long result = RedisUtil.zrem(KEY, order);
			System.out.println("^^^^^^^^处理完毕，订单ID：" + order + "删除结果：" + (result == 1));
		}
	}
	
	private static long removeMillis(Calendar cal) throws ParseException {
		String date = sdFormatter.format(cal.getTime());
		Date nowTime = sdFormatter.parse(date);
		System.out.println("--------当前时间：" + nowTime + "--------毫秒数" + nowTime.getTime());
		return nowTime.getTime();
	}
	
	public static void main(String[] args) {
//		initData();
		try {
			consumer();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}