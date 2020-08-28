package com.java.netty.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChannelFutures {
	private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
	private static final ByteBuf SOME_MSG_FROM_SOMEWHERE = Unpooled.buffer(1024);
	
	
	public static void addingChannelFutureListener() {
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		ByteBuf someMessage = SOME_MSG_FROM_SOMEWHERE;
		
		ChannelFuture future = channel.write(someMessage);
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture f) throws Exception {
				// TODO Auto-generated method stub
				if(!f.isSuccess()) {
					f.isDone();
					f.cause().printStackTrace();
					f.channel().close();
				}
			}
		});
	}
}
