package com.dragon.basic.java.lang.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
		
		for(int i = 1; i < 21; i++) {
			MyTask myTask = new MyTask(i);
			executor.execute(myTask);
			
			System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的数目：" + executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
		}
		
		executor.shutdown();
	}
	
	// 执行结果
//	线程池中线程数目：1，队列中等待执行的数目：0，已执行完的任务数目：0
//	正在执行task 1
//	线程池中线程数目：2，队列中等待执行的数目：0，已执行完的任务数目：0
//	线程池中线程数目：3，队列中等待执行的数目：0，已执行完的任务数目：0
//	正在执行task 3
//	线程池中线程数目：4，队列中等待执行的数目：0，已执行完的任务数目：0
//	线程池中线程数目：5，队列中等待执行的数目：0，已执行完的任务数目：0
//	线程池中线程数目：5，队列中等待执行的数目：1，已执行完的任务数目：0
//	线程池中线程数目：5，队列中等待执行的数目：2，已执行完的任务数目：0
//	线程池中线程数目：5，队列中等待执行的数目：3，已执行完的任务数目：0
//	线程池中线程数目：5，队列中等待执行的数目：4，已执行完的任务数目：0
//	线程池中线程数目：5，队列中等待执行的数目：5，已执行完的任务数目：0
//	线程池中线程数目：6，队列中等待执行的数目：5，已执行完的任务数目：0
//	线程池中线程数目：7，队列中等待执行的数目：5，已执行完的任务数目：0
//	线程池中线程数目：8，队列中等待执行的数目：5，已执行完的任务数目：0
//	线程池中线程数目：9，队列中等待执行的数目：5，已执行完的任务数目：0
//	线程池中线程数目：10，队列中等待执行的数目：5，已执行完的任务数目：0
//	正在执行task 5
//	正在执行task 15
//	正在执行task 13
//	正在执行task 11
//	正在执行task 4
//	正在执行task 2
//	正在执行task 12
//	正在执行task 14
//	task 2 执行完毕
//	task 12 执行完毕
//	task 14 执行完毕
//	task 15 执行完毕
//	正在执行task 8
//	task 1 执行完毕
//	正在执行task 10
//	task 3 执行完毕
//	正在执行task 9
//	正在执行task 7
//	task 13 执行完毕
//	task 11 执行完毕
//	task 4 执行完毕
//	正在执行task 6
//	task 5 执行完毕
//	task 8 执行完毕
//	task 6 执行完毕
//	task 9 执行完毕
//	task 7 执行完毕
//	task 10 执行完毕
	
	// 从执行结果可以看出，当线程池中线程的数目大于5时，便将任务放入任务缓存队列里面，当任务缓存队列满了之后，便创建新的线程。如果上面程序中，将for循环中改成执行20个任务，就会抛出任务拒绝异常了。

	
//	Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task test.java.lang.thread.pool.MyTask@13c6a22 rejected from java.util.concurrent.ThreadPoolExecutor@15c07d8[Running, pool size = 10, active threads = 10, queued tasks = 5, completed tasks = 0]
//			at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2048)
//			at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:821)
//			at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1372)
//			at test.java.lang.thread.pool.ThreadPoolExecutorTest.main(ThreadPoolExecutorTest.java:14)

}

class MyTask implements Runnable {
	
	private int taskNum;
	
	public MyTask(int num) {
		this.taskNum = num;
	}

	@Override
	public void run() {
		System.out.println("正在执行task " + taskNum);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("task " + taskNum + " 执行完毕");
	}
	
}