package com.java.mynetty.charpter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(65536),
				new WebSocketServerProtocolHandler("/websocket"), new TextFrameHandler(), new BinaryFrameHandler(),
				new ContinuationFrameHandler());
	}

	public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
			// TODO Auto-generated method stub

		}
	}

}
