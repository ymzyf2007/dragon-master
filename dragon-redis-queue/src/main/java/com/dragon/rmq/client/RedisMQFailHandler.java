package com.dragon.rmq.client;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.common.Message;
import com.dragon.rmq.common.RedisUtil;
import com.dragon.rmq.common.ThreadFactoryImpl;

public class RedisMQFailHandler {
	
	private static Log log = LogFactory.getLog(RedisMQFailHandler.class);

	private int handleThreadNums = 2;
	private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
	
	// 设置失败消息5分钟后重新入队列
	private long failExecuteTime = 1000 * 60 * 5;

	public RedisMQFailHandler() {
		this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(handleThreadNums, new ThreadFactoryImpl("DefaultMQFailHandler"));
	}

	public void handle(final Message message) throws MQClientException {
		scheduledThreadPoolExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				log.info("["+ message.getTopic() +"] rpush redis queue again...");
				RedisUtil.rpush(message.getTopic(), message.getBody());
			}
		}, failExecuteTime, TimeUnit.MILLISECONDS);
	}

}