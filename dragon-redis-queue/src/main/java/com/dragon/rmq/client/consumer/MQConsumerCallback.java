package com.dragon.rmq.client.consumer;

/**
 * 消费者回调接口
 *
 */
public interface MQConsumerCallback {
	
	public void onTask(byte[] body);

}