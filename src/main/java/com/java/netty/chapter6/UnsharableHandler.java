package com.java.netty.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UnsharableHandler extends ChannelInboundHandlerAdapter{
	private int count;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		count++;
		System.out.println("inboundBufferUpdated(...) called the "
				+ count + " time");
		ctx.fireChannelRead(msg);
	}
}
