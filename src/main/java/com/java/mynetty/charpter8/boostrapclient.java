package com.java.mynetty.charpter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class boostrapclient {
	public static void main(String[] args) throws InterruptedException {
		Bootstrap server = new Bootstrap();
		EventLoopGroup boss = new NioEventLoopGroup();
		server.group(boss)
		.localAddress(8001)
		.channel(NioSocketChannel.class)
		.handler(new SimpleChannelInboundHandler<ByteBuf>() {
			
			@Override
			public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
				// TODO Auto-generated method stub
				Attribute k = ctx.channel().attr(AttributeKey.valueOf("ID"));
				System.out.println(String.valueOf(k.get()));
			}

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
				// TODO Auto-generated method stub
				
			}
		})
		.remoteAddress(new InetSocketAddress("0.0.0.0", 8000));
		
		
		server.attr(AttributeKey.newInstance("ID"), 123);
		server.option(ChannelOption.SO_KEEPALIVE, true);
		ChannelFuture f = server.connect().sync();
		f.channel().writeAndFlush("haha");
	
	}
}
