package com.java.netty.chapter6;


import java.nio.charset.Charset;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

public class WriteHandlers {
	private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = null;
	private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE=null;
	
	public static void writeViaChannel() {
		ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
		Channel channel = ctx.channel();
		channel.write(Unpooled.copiedBuffer("Netty in Action",CharsetUtil.UTF_8));
	}
	
	public static void writeViaChannelPipeline() {
		ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
		ChannelPipeline pipeline = ctx.pipeline();
		pipeline.write(Unpooled.copiedBuffer("Netty in Action",CharsetUtil.UTF_8));
	}
	
	public static void writeViaChannelHandlerContext() {
		ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
		ctx.write(Unpooled.copiedBuffer("Netty in Action",Charset.forName("UTF-8")));
	}
}
