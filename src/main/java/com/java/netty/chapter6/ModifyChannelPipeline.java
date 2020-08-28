package com.java.netty.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

public class ModifyChannelPipeline {
	private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = null;

	public static void modifyPipeliine() {
		ChannelPipeline pipeline = CHANNEL_PIPELINE_FROM_SOMEWHERE;
		// 创建一个FirstHandler的实例
		FirstHandler firstHandler = new FirstHandler();
		pipeline.addLast("handle1",firstHandler);
		pipeline.addFirst("handle2",new SecondHandler());
		pipeline.addLast("handle3", new ThirdHandler());
		pipeline.remove("handle3");
		pipeline.remove(firstHandler);
		pipeline.replace("handler2","handler4",new FourthHandler());
	}

	private static final class FirstHandler extends ChannelHandlerAdapter {

	}

	private static final class SecondHandler extends ChannelHandlerAdapter {

	}

	private static final class ThirdHandler extends ChannelHandlerAdapter {

	}

	private static final class FourthHandler extends ChannelHandlerAdapter {

	}
}
