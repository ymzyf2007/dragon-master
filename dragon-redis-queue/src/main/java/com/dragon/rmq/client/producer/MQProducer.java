package com.dragon.rmq.client.producer;

import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.common.Message;

/**
 * 消息队列生产者接口
 *
 */
public interface MQProducer {
	
	/**
	 * 同步发送消息到队列
	 * @param message
	 */
	public void synSend(Message message) throws MQClientException;
	
	/**
	 * 异步发送消息队列
	 * @param message
	 */
	public void asyncSend(Message message) throws MQClientException;

}