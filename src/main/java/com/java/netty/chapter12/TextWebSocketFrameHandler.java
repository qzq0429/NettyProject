package com.java.netty.chapter12;

import java.util.Iterator;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.ChannelMatchers;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

public class TextWebSocketFrameHandler
	extends ChannelInboundHandlerAdapter{
	private final ChannelGroup group;
	
	public TextWebSocketFrameHandler(ChannelGroup group) {
		// TODO Auto-generated constructor stub
		this.group = group;
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
			ctx.pipeline().remove(HttpRequestHandler.class);
			group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
			group.add(ctx.channel());
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		    System.out.println("size:"+group.size());
		    System.out.println("msg的引用数:" + ((TextWebSocketFrame)msg).refCnt());
		    //if( ((TextWebSocketFrame)msg).text().length()==0 ) return;
			group.writeAndFlush(((TextWebSocketFrame)msg).retain());
			System.out.println("active: "+ctx.channel().isActive());
			ctx.fireChannelRead(msg);
			
			//ctx.fireChannelRead(msg);
			//if(ctx.channel().isActive())
			//ctx.fireChannelRead(msg);

	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("size:"+group.size());
		group.remove(ctx.channel());
		System.out.println("size:"+group.size());
		group.deregister(ChannelMatchers.is(ctx.channel()));
		System.out.println("size:"+group.size());
	}
	

}
