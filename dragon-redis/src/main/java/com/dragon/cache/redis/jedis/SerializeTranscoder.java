package com.dragon.cache.redis.jedis;

import java.io.Closeable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SerializeTranscoder {

	protected static Log logger = LogFactory.getLog(SerializeTranscoder.class);

	/**
	 * 序列化
	 * @param value
	 * @return
	 */
	public abstract byte[] serialize(Object value);

	/**
	 * 反序列化
	 * @param in
	 * @return
	 */
	public abstract Object deserialize(byte[] in);

	public void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				logger.info("Unable to close " + closeable, e);
			}
		}
	}

}