package com.java.netty.chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;

import io.netty.channel.Channel;

public class ScheduleExamples {
	private static final Channel CHANNEL_FROM_SOMEWHERE = null;
	
	public static void schedule() {
		ScheduledExecutorService executor = 
				Executors.newScheduledThreadPool(10);
		
		ScheduledFuture<?> future = executor.schedule(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Now it is 60 seconds later");
			}
		},60, TimeUnit.SECONDS);
		//一旦调度任务执行完成，就关闭ScheduledExecutorService 以释放资源
		executor.shutdown();
	}
	
	public static void scheduleViaEventLoop() {
		Channel ch = CHANNEL_FROM_SOMEWHERE;
		ScheduledFuture<?> future = ch.eventLoop().schedule(
				new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						
					}
				},60,TimeUnit.SECONDS
				);
	}
	
	public static void scheduleFixedViaEventLoop() {
		Channel ch = CHANNEL_FROM_SOMEWHERE;
		ScheduledFuture<?> future =ch.eventLoop().scheduleAtFixedRate(
				new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Run every 60 seconds");
					}
				}, 60, 60, TimeUnit.SECONDS);
	}
	
	public static void cancelingTaskUsingScheduledFuture() {
		Channel ch = CHANNEL_FROM_SOMEWHERE;
		
		ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
				new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
					System.out.println("Run every 60 seconds");	
					}
				},
				60, 
				60,
				TimeUnit.SECONDS);
		boolean mayInterruptIfRunning = false;
		future.cancel(mayInterruptIfRunning);
	}
}
