package com.java.netty.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

public class DiscardOutboundHandler
	extends ChannelOutboundHandlerAdapter{

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		//释放丢弃出站消息
		ReferenceCountUtil.release(msg);
		//通知ChannelPromise数据已经被处理了
		promise.setSuccess();
	}
}
