package com.dragon.rmq.client.consumer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dragon.rmq.client.consumer.listener.MessageListener;
import com.dragon.rmq.client.consumer.listener.RedisMessageListener;
import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.common.ThreadFactoryImpl;

/**
 * 基于Redis实现的消费者
 *
 */
public class RedisMQConsumer implements MQConsumer {
	
	private static Log log = LogFactory.getLog(RedisMQConsumer.class);
	
	private ServiceState serviceState = ServiceState.CREATE_JUST;	// 服务枚举状态
	
	private String consumerName;	// 消费者名称
	private int pullThreadNums = 20;	// 线程池大小
	private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
	private ConcurrentHashMap<String, MQConsumerCallback> callBackMap = new ConcurrentHashMap<String, MQConsumerCallback>();
	
	private PullMessageService pullMessageService = new PullMessageService();
	private MessageListener redisMessageListener = new RedisMessageListener(this);
	
	/**
	 * 构造方法，设置消费者名称
	 * @param consumerName
	 */
	public RedisMQConsumer(String consumerName) {
		this.consumerName = consumerName;
	}
	
	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	
	public int getPullThreadNums() {
		return pullThreadNums;
	}

	public void setPullThreadNums(int pullThreadNums) {
		this.pullThreadNums = pullThreadNums;
	}

	/**
	 * 启动消费者
	 * @throws MQClientException
	 */
	@Override
	public void start() throws MQClientException {
		switch(serviceState) {
		case CREATE_JUST:
			log.info("RedisMQConsumer CREATE_JUST started...");
            serviceState = ServiceState.START_FAILED;
            scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(pullThreadNums, new ThreadFactoryImpl("RedisPullThread-"+ consumerName));
            // 设置redis消息监听
    		pullMessageService.setMessageListener(redisMessageListener);
    		// 开启拉取消息服务
    		pullMessageService.start();
            
    		serviceState = ServiceState.RUNNING;
    		break;
        case RUNNING:
        case START_FAILED:
        case SHUTDOWN_ALREADY:
        	throw new MQClientException("RedisMQConsumer Server shutdown", null);
        default:
        	break;
        }
	}
	
	class TaskImpl implements Runnable {
		private String topic;
		private byte[] body;
		
		public TaskImpl(String topic, byte[] body) {
			this.topic = topic;
			this.body = body;
		}
		public void run() {
			MQConsumerCallback callBack = callBackMap.get(topic);
			if (callBack != null) {
				callBack.onTask(body);
			}
        }
	}
	
	public void putTask(String topic, byte[] body) {
		TaskImpl task = new TaskImpl(topic, body);
		scheduledThreadPoolExecutor.schedule(task, 0, TimeUnit.MILLISECONDS);
	}

	/**
	 * 停止消费者
	 */
	@Override
	public void shutdown() {
		if(scheduledThreadPoolExecutor != null) {
			scheduledThreadPoolExecutor.shutdown();
		}
		if(!pullMessageService.isStoped()) {
			pullMessageService.shutdown();
		}
	}

	/**
	 * 注册回调
	 * @param topic
	 * @param callBack
	 */
	@Override
	public void regiestCallBack(String topic, MQConsumerCallback callBack) {
		pullMessageService.subscribe(topic);
		callBackMap.put(topic, callBack);
	}

	/**
	 * 订阅主题
	 */
	@Override
	public void subscribe(String topic) {
		pullMessageService.subscribe(topic);
	}

	/**
	 * 取消订阅
	 */
	@Override
	public void unSubscribe(String topic) {
		pullMessageService.unSubscribe(topic);
	}

}