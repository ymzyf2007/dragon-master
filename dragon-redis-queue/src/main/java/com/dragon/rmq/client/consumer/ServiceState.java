package com.dragon.rmq.client.consumer;

/**
 * 服务枚举状态
 */
public enum ServiceState {
	
	/**
     * Service just created,not start
     */
    CREATE_JUST,
    
    /**
     * Service Running
     */
    RUNNING,
    
    /**
     * Service shutdown
     */
    SHUTDOWN_ALREADY,
    
    /**
     * Service Start failure
     */
    START_FAILED;

}