package com.dragon.rmq.client.consumer.listener;

/**
 * 消息监听
 * 
 */
public interface MessageListener {
	
	void onMessage(String topic, byte[] body);

}