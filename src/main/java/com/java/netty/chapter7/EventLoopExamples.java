package com.java.netty.chapter7;

import java.util.Collections;
import java.util.List;

public class EventLoopExamples {
	
	public static void executeTaskInEventLoop() {
		boolean terminated = true;
		//...
		while(!terminated) {
			List<Runnable> readyEvents = blockUntilEventsReady();
			for(Runnable ev: readyEvents) {
				ev.run();
			}
		}
	}
	
	private static final List<Runnable> blockUntilEventsReady(){
		return Collections.<Runnable>singletonList(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
}
