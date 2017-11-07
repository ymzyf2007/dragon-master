package com.dragon.rmq.common;

import java.io.Serializable;

/**
 * 消息实体
 * 
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -1723488606989781037L;

	// 消息主题
	private String topic;
	// 消息实体
	private byte[] body;

	public Message() {
	}

	public Message(String topic, byte[] body) {
		this.topic = topic;
		this.body = body;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

}