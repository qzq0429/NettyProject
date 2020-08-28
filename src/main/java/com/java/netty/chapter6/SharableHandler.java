package com.java.netty.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SharableHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("channel read message " + msg);
		//记录方法调用，并转发给下一个ChannelHandler
		ctx.fireChannelRead(msg);
	}
}
