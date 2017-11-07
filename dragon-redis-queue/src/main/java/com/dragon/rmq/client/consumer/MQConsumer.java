package com.dragon.rmq.client.consumer;

import com.dragon.rmq.client.exception.MQClientException;

public interface MQConsumer {

	/**
	 * 启动消费者
	 * @throws MQClientException
	 */
	void start() throws MQClientException;

	/**
	 * 停止消费者
	 */
	void shutdown();

	/**
	 * 注册回调
	 * @param topic
	 * @param callBack
	 */
	void regiestCallBack(final String topic, final MQConsumerCallback callBack);

	/**
	 * 订阅主题
	 * @param topic
	 */
	void subscribe(String topic);

	/**
	 * 取消订阅
	 * @param topic
	 */
	void unSubscribe(String topic);

}