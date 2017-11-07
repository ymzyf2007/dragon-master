package com.dragon.basic.java.util.concurrent.enterprise.eg1;

public abstract class AbstractServer implements Server, Runnable {
	
	private Thread thread;
	private boolean running = false;
	private long interval = 5 * 1000;	// 时间间隔，默认5秒
	
	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void startup() {
		running = true;
		thread = new Thread(this);
		thread.setName(getName());
		thread.start();
	}

	@Override
	public void shutdown() {
		if(running) {
			running = false;
			thread.interrupt();	// 中断线程
		}
	}
	
	@Override
	public void run() {
		System.out.println(getName() + " started.");
		while(running) {
			try {
				if(!doService()) {
					Thread.sleep(interval);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(getName() + " stoped.");
	}

	protected abstract boolean doService() throws Exception;
	
}