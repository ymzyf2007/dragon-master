package com.dragon.rmq.client.producer;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.common.Message;
import com.dragon.rmq.common.RedisUtil;

public class RedisMQProducer implements MQProducer {
	
	private static Log log = LogFactory.getLog(RedisMQProducer.class);
	
	private ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

	@Override
	public void synSend(Message message) throws MQClientException {
		try {
			log.info("Redis Producer Syn Send is message {} " + message);
			RedisUtil.rpush(message.getTopic(), message.getBody());
        } catch (Exception e) {
        	log.error("Redis Producer Syn Send error.", e);
        	throw new MQClientException("Redis Producer Syn Send fail", e);
        }
	}

	@Override
	public void asyncSend(final Message message) throws MQClientException {
		try {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					log.info("Redis Producer Async Send is message {} " + message);
					RedisUtil.rpush(message.getTopic(), message.getBody());
				}
			});
        } catch (Exception e) {
        	log.error("Redis Producer Async Send error.", e);
        	throw new MQClientException("Redis Producer Async Send fail", e);
        }
	}

}