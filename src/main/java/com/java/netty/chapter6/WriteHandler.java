package com.java.netty.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class WriteHandler extends ChannelHandlerAdapter {
	private ChannelHandlerContext ctx;
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		this.ctx = ctx;
	}
	
	public void send(String msg) {
		// TODO Auto-generated method stub
		//使用之前存储的到ChannelHandlerContext的引用来发送消息
		ctx.writeAndFlush(msg);
	}
}
