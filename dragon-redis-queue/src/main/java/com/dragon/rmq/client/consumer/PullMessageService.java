package com.dragon.rmq.client.consumer;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dragon.rmq.client.consumer.listener.MessageListener;
import com.dragon.rmq.common.RedisUtil;
import com.dragon.rmq.common.ServiceThread;

/**
 * 后台拉取消息服务
 * 
 */
public class PullMessageService extends ServiceThread {

	private static final Log log = LogFactory.getLog(PullMessageService.class);

	private Map<String, String> topics = new ConcurrentHashMap<String, String>();

	private MessageListener messageListener;

	/**
	 * 订阅主题
	 * @param topic
	 */
	public void subscribe(String topic) {
		topics.put(topic, topic);
		log.info("subscribe topic["+ topic +"] success");
	}

	/**
	 * 取消订阅
	 * @param topic
	 */
	public void unSubscribe(String topic) {
		topics.remove(topic);
		log.info("unSubscribe topic["+ topic +"] success");
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void run() {
		log.info(getServiceName() + " service started...");
		if (topics.size() == 0) {
			log.info("There is no topic,Please register again to retry");
			return;
		}
		while (!stoped) {
			waitForRunning(100);
			// 遍历所有的主题去拉取数据
			Iterator<Entry<String, String>> it = topics.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> next = it.next();
				byte[] body = null;
				body = RedisUtil.lpop(next.getValue());
				if (body != null && body.length > 0) {
					// 发布改主题消息事件监听
					log.info("start send subject event message listener");
					messageListener.onMessage(next.getValue(), body);
				}
			}
		}
	}

	@Override
	public String getServiceName() {
		return PullMessageService.class.getSimpleName();
	}

}