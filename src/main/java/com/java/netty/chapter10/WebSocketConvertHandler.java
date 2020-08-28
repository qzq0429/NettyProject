package com.java.netty.chapter10;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame, WebSocketConvertHandler.MyWebSocketFrame> {

	
	
	public static final class MyWebSocketFrame{
		public enum FrameType{
			BINARY,
			CLOSE,
			PING,
			PONG,
			TEXT,
			CONTINUATION
		}
		private final FrameType type;
		private final ByteBuf data;
		
		public MyWebSocketFrame(FrameType type,ByteBuf data) {
			this.type = type;
			this.data = data;
		}
		public FrameType getType() {
			return type;
		}
		
		public ByteBuf getData() {
			return data;
		}
		
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf payLoad = msg.getData().duplicate().retain();
		switch(msg.getType()) {
		case BINARY:
			out.add(new BinaryWebSocketFrame(payLoad));
			break;
		case TEXT:
			out.add(new TextWebSocketFrame(payLoad));
			break;
		default:
			throw new IllegalStateException(
					"Unsupported websocket msg " + msg);
			
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf payload = msg.content().duplicate().retain();
		if(msg instanceof BinaryWebSocketFrame) {
			out.add(new MyWebSocketFrame(
					MyWebSocketFrame.FrameType.BINARY,payload));
		}
	}
}


