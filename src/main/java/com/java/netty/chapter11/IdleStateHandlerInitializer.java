package com.java.netty.chapter11;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		//IdleStateHandler 将在出发时发送一个IdleStateEvent事件
		pipeline.addLast(new IdleStateHandler(0,0,60,TimeUnit.SECONDS));
		
		pipeline.addLast(new HeartbeatHandler());
	}
	
	public static final class HeartbeatHandler
		extends ChannelInboundHandlerAdapter{
		private static final ByteBuf HEARTBEAT_SEQUENCE = 
				Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARBEAT",CharsetUtil.ISO_8859_1));
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			// TODO Auto-generated method stub
			if(evt instanceof IdleStateEvent) {
				ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
				.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			}else {
				super.userEventTriggered(ctx, evt);
			}
		}
	}

}
