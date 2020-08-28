package com.java.netty.chapter6;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 添加 ChannelFutureListener 到 ChannelPromise
 * 
 * @author Michael Qi
 *
 */
public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {
@Override
public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
	// TODO Auto-generated method stub
	promise.addListener(new ChannelFutureListener() {

		public void operationComplete(ChannelFuture f) throws Exception {
			// TODO Auto-generated method stub
			if(!f.isSuccess()) {
				f.cause().printStackTrace();
				f.channel().close();
			}
		}
		
	});
}
}
