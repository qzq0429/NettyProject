package com.java.netty.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel>{
	private final ChannelGroup group;
	
	public ChatServerInitializer(ChannelGroup group) {
		this.group = group;
	}
	
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new ChunkedWriteHandler());
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		pipeline.addLast(new HttpRequestHandler("/ws"));
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		pipeline.addLast(new TextWebSocketFrameHandler(group));
		pipeline.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
				System.out.println(msg.text());
				//ctx.fireChannelRead(msg);
				//ctx.fireChannelRead(msg);
			}
		});
		
	};
}
