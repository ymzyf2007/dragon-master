package test.com.dragon.rmq.client;

import com.dragon.rmq.client.RedisMQFailHandler;
import com.dragon.rmq.client.consumer.MQConsumerCallback;
import com.dragon.rmq.client.consumer.RedisMQConsumer;
import com.dragon.rmq.client.exception.MQClientException;
import com.dragon.rmq.common.Message;

public class ConsumerTest {

	public static void main(String[] args) throws MQClientException {
		RedisMQConsumer consumer = new RedisMQConsumer("documentPraise");
		consumer.regiestCallBack("DOC_PAGE_VIEW", new MQConsumerCallback() {
			public void onTask(byte[] body) {
				try {
					System.out.println("return：" + Thread.currentThread().getName() + ":" + body);
				} catch (Exception e) {
					// 消费消息时执行异常，使用默认异常处理器处理
					try {
						Message message = new Message("DOC_PAGE_VIEW", body);
						new RedisMQFailHandler().handle(message);
					} catch (MQClientException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		consumer.start();
	}

}