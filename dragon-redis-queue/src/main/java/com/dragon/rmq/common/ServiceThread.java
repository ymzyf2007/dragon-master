package com.dragon.rmq.common;

/**
 * 后台服务线程类
 * 
 */
public abstract class ServiceThread implements Runnable {
	// 执行线程
	protected final Thread thread;
	// 线程回收时间，默认90S
	private static final long JoinTime = 90 * 1000;
	// 是否已经被Notify过
	protected volatile boolean hasNotified = false;
	// 线程是否已经停止
	protected volatile boolean stoped = false;

	public ServiceThread() {
		thread = new Thread(this, getServiceName());
	}

	// 服务线程名称，由子类指定
	public abstract String getServiceName();

	// 线程启动
	public void start() {
		thread.start();
	}

	public void shutdown() {
		shutdown(false);
	}

	public void stop() {
		stop(false);
	}

	public void makeStop() {
		stoped = true;
	}

	public void stop(final boolean interrupt) {
		stoped = true;
		synchronized (this) {
			if (!hasNotified) {
				hasNotified = true;
				notify();
			}
		}

		if (interrupt) {
			thread.interrupt();
		}
	}

	public void shutdown(final boolean interrupt) {
		stoped = true;
		synchronized (this) {
			if (!hasNotified) {
				hasNotified = true;
				notify();
			}
		}

		try {
			if (interrupt) {
				thread.interrupt();
			}

			if (!thread.isDaemon()) {
				thread.join(getJointime());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void wakeup() {
		synchronized (this) {
			if (!hasNotified) {
				hasNotified = true;
				notify();
			}
		}
	}

	protected void waitForRunning(long interval) {
		synchronized (this) {
			if (hasNotified) {
				hasNotified = false;
				onWaitEnd();
				return;
			}

			try {
				wait(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				hasNotified = false;
				onWaitEnd();
			}
		}
	}

	protected void onWaitEnd() {
	}

	public boolean isStoped() {
		return stoped;
	}

	public long getJointime() {
		return JoinTime;
	}
	
}