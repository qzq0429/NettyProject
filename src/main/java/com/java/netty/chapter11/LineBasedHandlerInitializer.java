package com.java.netty.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LineBasedFrameDecoder(64*1024));
		pipeline.addLast(new FrameHandler());
	}
	
	public static final class FrameHandler
		extends SimpleChannelInboundHandler<ByteBuf>{
		protected void channelRead0(io.netty.channel.ChannelHandlerContext ctx, ByteBuf msg) throws Exception {};
	}

}
