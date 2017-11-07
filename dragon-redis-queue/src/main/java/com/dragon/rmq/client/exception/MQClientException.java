package com.dragon.rmq.client.exception;

public class MQClientException extends Exception {

	private static final long serialVersionUID = -1914712170562065574L;

	private final int responseCode;
	private final String errorMessage;

	public MQClientException(String errorMessage, Throwable cause) {
		this.responseCode = -1;
		this.errorMessage = errorMessage;
	}

	public MQClientException(int responseCode, String errorMessage) {
		this.responseCode = responseCode;
		this.errorMessage = errorMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}