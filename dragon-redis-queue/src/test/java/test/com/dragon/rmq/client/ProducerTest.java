package test.com.dragon.rmq.client;

import org.junit.Test;

import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.client.producer.MQProducer;
import com.dragon.rmq.client.producer.RedisMQProducer;
import com.dragon.rmq.common.Message;

public class ProducerTest {

	/**
	 * 同步发送测试
	 */
	@Test
	public void synSend() {
		MQProducer producer = new RedisMQProducer();
		for(int i = 0; i < 1000; i++) {
			Message message = new Message("order", ("Hello syn RedisMQ" + i).getBytes());
			try {
				producer.synSend(message);
			} catch (MQClientException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 异步发送测试
	 */
	@Test
	public void asyncSend() {
		MQProducer producer = new RedisMQProducer();
		try {
			for(int i = 0; i < 1000; i++) {
				Message message = new Message("order", ("Hello async" + i).getBytes());
				producer.asyncSend(message);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

}