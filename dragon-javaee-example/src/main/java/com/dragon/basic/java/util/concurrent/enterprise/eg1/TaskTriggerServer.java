package com.dragon.basic.java.util.concurrent.enterprise.eg1;

public class TaskTriggerServer extends AbstractServer {

	/**
	 * 任务触发器，一般用来扫描表用，间隔几秒或几分钟扫描一些任务表，看是否有可执行任务，如果无，则直接返回false，然后线程休眠间隔时间，再去扫描
	 */
	@Override
	protected boolean doService() throws Exception {
		long startTime = System.currentTimeMillis();
		System.out.println("find executable task completed, 100 found. consuming " + (System.currentTimeMillis() - startTime) + " millisecond");
		return false;
	}

}