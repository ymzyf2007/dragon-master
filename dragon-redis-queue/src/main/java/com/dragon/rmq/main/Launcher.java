package com.dragon.rmq.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dragon.rmq.client.RedisMQFailHandler;
import com.dragon.rmq.client.consumer.MQConsumer;
import com.dragon.rmq.client.consumer.MQConsumerCallback;
import com.dragon.rmq.client.consumer.RedisMQConsumer;
import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.common.Message;

public class Launcher {
	
	public static final String DOC_PAGE_VIEW = "DOC_PAGE_VIEW";
	public static final String INFO_SEARCH_TOPIC = "INFO_SEARCH_TOPIC";
	
	private static Map<String, MQConsumerCallback> callBackMap = new HashMap<String, MQConsumerCallback>();
	static {
		callBackMap.put(DOC_PAGE_VIEW, new DocPageViewMQConsumerCallback());
		callBackMap.put(INFO_SEARCH_TOPIC, new InfoSearchMQConsumerCallback());
	}
	
	public static void main(String[] args) {
		Log log = LogFactory.getLog(Launcher.class);
		if (args.length == 1) {
			if ("start".equalsIgnoreCase(args[0])) {
				log.info("redis message queue consumer start...");
				try {
					MQConsumer consumer = new RedisMQConsumer("RedisMQConsumer");
					for(Iterator<String> it = callBackMap.keySet().iterator(); it.hasNext();) {
						String topic = it.next();
						consumer.regiestCallBack(topic, callBackMap.get(topic));
					}
					consumer.start();
					
					log.info("redis message queue consumer start success.");
				} catch (Exception e1) {
					e1.printStackTrace();
					log.error("redis message queue consumer start failed", e1);
				}
			} else if ("stop".equalsIgnoreCase(args[0])) {
				log.info("redis message queue consumer stoped");
			}
		}
	}

}

/**
 * 页面浏览量消费者回调
 */
class DocPageViewMQConsumerCallback implements MQConsumerCallback {
	@Override
	public void onTask(byte[] body) {
		try {
			System.out.println("return：" + Thread.currentThread().getName() + ":" + body);
		} catch (Exception e) {
			// 消费消息时执行异常，使用默认异常处理器处理
			try {
				Message message = new Message(Launcher.DOC_PAGE_VIEW, body);
				new RedisMQFailHandler().handle(message);
			} catch (MQClientException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class InfoSearchMQConsumerCallback implements MQConsumerCallback {
	@Override
	public void onTask(byte[] body) {
		try {
			System.out.println("return：" + Thread.currentThread().getName() + ":" + body);
		} catch (Exception e) {
			// 消费消息时执行异常，使用默认异常处理器处理
			try {
				Message message = new Message(Launcher.INFO_SEARCH_TOPIC, body);
				new RedisMQFailHandler().handle(message);
			} catch (MQClientException e1) {
				e1.printStackTrace();
			}
		}
	}
}