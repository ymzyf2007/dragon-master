package com.dragon.rmq.client.consumer.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dragon.rmq.client.consumer.RedisMQConsumer;

/**
 * Redis消息监听
 * 
 */
public class RedisMessageListener implements MessageListener {
	
	private static Log log = LogFactory.getLog(RedisMessageListener.class);

	private RedisMQConsumer redisMQConsumer;

	public RedisMessageListener(RedisMQConsumer redisMqConsumer) {
		this.redisMQConsumer = redisMqConsumer;
	}

	@Override
	public void onMessage(String topic, byte[] body) {
		log.info("[" + topic + "] receive new message...");
		redisMQConsumer.putTask(topic, body);
	}

}